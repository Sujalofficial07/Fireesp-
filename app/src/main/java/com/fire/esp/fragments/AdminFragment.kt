package com.fire.esp.fragments

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import com.fire.esp.data.SupabaseClientManager
import com.fire.esp.data.Tournament
import com.fire.esp.databinding.ActivityAdminBinding
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class AdminFragment : Fragment() {

    private lateinit var binding: ActivityAdminBinding
    private val scope = MainScope()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ActivityAdminBinding.inflate(inflater, container, false)

        binding.btnAddTournament.setOnClickListener { addTournament() }
        binding.btnEditTournament.setOnClickListener { editTournament() }
        binding.btnDeleteTournament.setOnClickListener { deleteTournament() }

        return binding.root
    }

    private fun addTournament() {
        val dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_tournament, null)
        val nameInput = dialogView.findViewById<EditText>(R.id.etTournamentName)
        val descInput = dialogView.findViewById<EditText>(R.id.etTournamentDesc)

        AlertDialog.Builder(requireContext())
            .setTitle("Add Tournament")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val name = nameInput.text.toString()
                val desc = descInput.text.toString()
                if (name.isNotEmpty() && desc.isNotEmpty()) {
                    scope.launch {
                        SupabaseClientManager.addTournament(
                            Tournament(
                                name = name,
                                description = desc
                            )
                        )
                    }
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun editTournament() {
        // TODO: Fetch tournaments, show in a dialog or list, then update selected tournament
    }

    private fun deleteTournament() {
        // TODO: Fetch tournaments, show in a dialog or list, then delete selected tournament
    }
}
