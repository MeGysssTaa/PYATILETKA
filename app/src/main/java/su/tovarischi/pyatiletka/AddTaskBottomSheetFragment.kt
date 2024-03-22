package su.tovarischi.pyatiletka

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class AddTaskBottomSheetFragment(
    private var taskCategory: SovietTask.Category,
    private var availableCategories: List<SovietTask.Category>,
    private val interactionListener: InteractionListener
) : BottomSheetDialogFragment(R.layout.fragment_add_task_bottom_sheet) {
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
        val categoryAdapter = CategoryArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            availableCategories
        )
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        categorySpinner.adapter = categoryAdapter

        // Set the selected category
        categorySpinner.setSelection(categoryAdapter.getPosition(taskCategory))

        addTaskButton.setOnClickListener {
            val taskName = taskNameEditText.text.toString()
            val taskDescription = taskDescriptionEditText.text.toString()
            val selectedCategoryPosition = categorySpinner.selectedItemPosition
            val taskCategory = categoryAdapter.getItem(selectedCategoryPosition)

            interactionListener.saveNewTask(taskName, taskDescription, taskCategory)

            dismiss()
        }
    }

    interface InteractionListener {
        fun saveNewTask(taskName: String, taskDescription: String, taskCategory: SovietTask.Category?)
    }

}