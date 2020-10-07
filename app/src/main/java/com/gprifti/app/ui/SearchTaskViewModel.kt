package com.gprifti.app.ui

import android.app.Application
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.gprifti.app.db.TaskTable
import com.gprifti.app.models.BaseModel
import com.gprifti.app.models.Task
import com.gprifti.app.repository.SearchRepository
import com.gprifti.app.util.InternetConnection
import kotlinx.coroutines.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.util.ArrayList

@RequiresApi(Build.VERSION_CODES.M)
class SearchTaskViewModel(
    app: Application,
    private val searchRepository: SearchRepository
) : AndroidViewModel(app) {

    // contains the result of search, online and offline
    var searchResult = MutableLiveData<List<Task>>()

    //contains the value of the searchView
    private var searchView = MutableLiveData<String>()

    // contains the filter type
    var filterType = MutableLiveData<String>()

    // temporary filter type
    private var tempFilter: Int = 0

    // contains the state of the whole view, ex: loading, show result, etc
    var viewState = MutableLiveData<Int>()

    //the context
    private val context = getApplication<Application>().applicationContext

    //using this enum to keep track of the filter type
    enum class FilterType(val filter: String) {
        ALL(""), ACTIVE("1"), ARCHIVED("18")
    }

    // using this enum to keep track of the state view
    enum class SearchViewState(viewState: Int) {
        INITIAL_SCREEN(0), LOADING(1), SEARCH_RESULT(2), ERROR(3), RESULT_NOT_FOUND(4)
    }

    init {
        // filter type should be all initially
        filterType.value = FilterType.ALL.filter

        //showing the initial screen
        viewState.value = SearchViewState.INITIAL_SCREEN.ordinal

        // searchView is empty in the begin
        searchView.value = String()
    }

    /** -when one of filter button is clicked, this method gets called directly form xml and change the filterType value if the
      filter was not selected before */
    fun changeFilterState(stateValue: Int) {
        if (tempFilter == stateValue) return
        tempFilter = stateValue
        when (stateValue) {
            0 -> filterType.value = FilterType.ALL.filter
            1 -> filterType.value = FilterType.ACTIVE.filter
            2 -> filterType.value = FilterType.ARCHIVED.filter
        }

        // making a simple check if the search if is empty
        if (searchView.value.toString().isNotBlank())
            searchForTasks(searchView.value.toString())
    }

    fun searchForTasks(searchViewValue: String) {
        //using the viewModelScope to call this in UI thread, the viewModelScope gets cancelled by itself
        viewModelScope.launch {
            if (searchViewValue.isNotEmpty())
                searchView.value = searchViewValue
            viewState.value = SearchViewState.LOADING.ordinal

            // check for the internet connection, if is available search using the endpoint, otherwise search in local Db
            if (InternetConnection.isOnline(context)) searchCall(
                searchViewValue,
                filterType.value.toString()
            )
            else searchInDb(searchViewValue, filterType.value.toString())
        }
    }

    /** this call is happening in the background thread */
    private suspend fun searchCall(searchText: String, statusValue: String) {
        val payload = createPayload(searchText, statusValue)
        val response = searchRepository.search(payload)

        insertIntoDb(response)
        manageViewState(response)
    }

    /** preparing the payload before making the network call */
    private fun createPayload(textValue: String, statusValue: String): String {
        val payloadObj = JSONObject()
        try {
            payloadObj.put("text", textValue)
            payloadObj.put("status", statusValue)
        } catch (e: JSONException) {
            e.printStackTrace()
        }
        return payloadObj.toString()
    }

    /** clear the viewState and the searchView when clicking on the close button */
    fun clearSearchResult() {
        searchResult.value = emptyList()
        searchView.value = String()
        viewState.value = SearchViewState.INITIAL_SCREEN.ordinal
    }

    /** storing the project name and the task name into a list as Task object */
    private fun mapTask(response: Response<BaseModel>): List<Task> {

        val mappedTaskList = ArrayList<Task>()

        val response = response.body()?.results

        val taskList = response?.tasks
        val sectionsList = response?.sections
        val projectsList = response?.projects

        taskList?.forEach { task ->
            sectionsList?.forEach { section ->
                projectsList?.forEach { project ->
                    if (task?.sectionId == section?.id && section?.projectId == project?.id)
                        mappedTaskList.add(Task(project?.name, task?.name, task?.status))
                }
            }
        }
        return mappedTaskList
    }

    /** insert into db */
    private suspend fun insertIntoDb(response: Response<BaseModel>) {
        val taskList = mapTask(response)
        taskList.forEach { task ->
            searchRepository.insertTask(
                TaskTable(
                    task.projectName,
                    task.taskName,
                    task.status
                )
            )
        }
    }

    /** update the view state depending on the network call response */
    private fun manageViewState(response: Response<BaseModel>) {
        if (response.isSuccessful) {
            searchResult.value = mapTask(response)
            if (mapTask(response).isEmpty()) {
                viewState.value = SearchViewState.RESULT_NOT_FOUND.ordinal
            } else {
                viewState.value = SearchViewState.SEARCH_RESULT.ordinal
            }
        } else {
            viewState.value = SearchViewState.ERROR.ordinal
        }
    }

    /** search in db */
    private suspend fun searchInDb(search: String, filter_type: String) {
        if (filter_type == FilterType.ALL.filter) {
            searchResult.value = searchRepository.readAllTask(search)
        } else {
            searchResult.value = searchRepository.readActiveArchivedTask(search, filter_type)
        }
        if (searchResult.value?.size != 0)
            viewState.value = SearchViewState.SEARCH_RESULT.ordinal
        else
            viewState.value = SearchViewState.RESULT_NOT_FOUND.ordinal
    }
}