package com.fire.esp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.fire.esp.R
import com.fire.esp.fragments.AdminFragment
import com.fire.esp.fragments.LeaderboardFragment
import com.fire.esp.fragments.ProfileFragment
import com.fire.esp.fragments.TournamentFragment

class HomeActivity : AppCompatActivity() {
private lateinit var binding: ActivityHomeBinding

override fun onCreate(savedInstanceState: Bundle?) {
super.onCreate(savedInstanceState)
binding = ActivityHomeBinding.inflate(layoutInflater)
setContentView(binding.root)

// Default fragment
openFragment(TournamentFragment())

binding.bottomNavigation.setOnItemSelectedListener { menuItem ->
when(menuItem.itemId){
R.id.menuTournaments -> openFragment(TournamentFragment())
R.id.menuLeaderboard -> openFragment(LeaderboardFragment())
R.id.menuProfile -> openFragment(ProfileFragment())
R.id.menuAdmin -> openFragment(AdminFragment())
}
true
}
}

private fun openFragment(fragment: Fragment){
supportFragmentManager.beginTransaction()
.replace(R.id.containerHome, fragment)
.commit()
}
}