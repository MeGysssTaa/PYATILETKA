package su.tovarischi.pyatiletka

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTaskBottomSheetFragment : BottomSheetDialogFragment(R.layout.fragment_add_task_bottom_sheet) {

    companion object {
        private const val ARG_TASK_CATEGORY = "task_category"
        private const val ARG_AVAILABLE_CATEGORIES = "available_categories"
    }


    private var taskCategory: SovietTask.Category? = null
    private var availableCategories: List<SovietTask.Category>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let { it ->
            taskCategory = SovietTask.Category.valueOf(it.getString(ARG_TASK_CATEGORY) ?: return@let)
            availableCategories = it.getStringArrayList(ARG_AVAILABLE_CATEGORIES)?.map { SovietTask.Category.valueOf(it) }
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_add_task_bottom_sheet, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val taskNameEditText = view.findViewById<EditText>(R.id.task_name_edit_text)
        val taskDescriptionEditText = view.findViewById<EditText>(R.id.task_description_edit_text)
        val addTaskButton = view.findViewById<ImageButton>(R.id.add_task_button)
        val categorySpinner = view.findViewById<Spinner>(R.id.category_spinner)

        addTaskButton.isEnabled = false

        taskNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                addTaskButton.isEnabled = s?.isNotEmpty() ?: false
                addTaskButton.isClickable = s?.isNotEmpty() ?: false
            }

            override fun afterTextChanged(s: Editable?) {}
        })

        // Set up the category spinner
        val categoryAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            availableCategories?.map { it.name } ?: emptyList()
        )
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = categoryAdapter

        // Set the selected category
        categorySpinner.setSelection(categoryAdapter.getPosition(taskCategory?.name))


        addTaskButton.setOnClickListener {
            val taskName = taskNameEditText.text.toString()
            val taskDescription = taskDescriptionEditText.text.toString()


            // TODO save new Task


            dismiss()
        }
    }
}