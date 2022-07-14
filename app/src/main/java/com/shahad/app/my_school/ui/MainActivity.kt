package com.shahad.app.my_school.ui

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.asLiveData
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.shahad.app.my_school.R
import com.shahad.app.my_school.ui.duty.AssignmentScreen
import com.shahad.app.my_school.ui.duty.AssignmentStudentViewModel
import com.shahad.app.my_school.ui.duty.AssignmentTeacherViewModel
import com.shahad.app.my_school.ui.home.HomeScreen
import com.shahad.app.my_school.ui.home.manger.HomeMangerViewModel
import com.shahad.app.my_school.ui.home.student.HomeStudentViewModel
import com.shahad.app.my_school.ui.home.teacher.HomeTeacherViewModel
import com.shahad.app.my_school.ui.identity.IdentityActivity
import com.shahad.app.my_school.ui.main.MainViewModel
import com.shahad.app.my_school.ui.notification.NotificationScreen
import com.shahad.app.my_school.ui.notification.NotificationViewModel
import com.shahad.app.my_school.ui.profile.MangerProfileViewModel
import com.shahad.app.my_school.ui.profile.ProfileScreen
import com.shahad.app.my_school.ui.profile.StudentProfileViewModel
import com.shahad.app.my_school.ui.profile.TeacherProfileViewModel
import com.shahad.app.my_school.ui.register.Role
import com.shahad.app.my_school.ui.users.UsersScreen
import com.shahad.app.my_school.util.extension.toRole
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity: ComponentActivity() {

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val role = viewModel.role.asLiveData().observeAsState()
            val navController = rememberNavController()
            Screen(
                (intent.getStringExtra("ROLE") ?: role.value ),
                navController
            )
        }
    }

    private fun navToIdentity() {
        startActivity(Intent(this, IdentityActivity::class.java))
        finish()
    }

    @Composable
    fun Screen(role: String?, navController: NavHostController){
        when(role?.toRole()){
            Role.TEACHER -> TeacherScreens(navController = navController)
            Role.STUDENT -> StudentScreens(navController = navController)
            Role.MANGER -> MangerScreens(navController = navController)
            null -> {}
        }
    }


    @Composable
    fun StudentScreens(navController: NavHostController){
        BasicScreen(
            screens = listOf(Screen.Home, Screen.Assignment, Screen.Notification, Screen.Profile),
            navController = navController
        ) {
            composable(
                route = Screen.Home.route,
                content = {
                    val viewModel: HomeStudentViewModel by viewModels()
                    HomeScreen(navController = navController, role = Role.STUDENT, viewModel = viewModel)
                }
            )
            composable(
                route = Screen.Assignment.route,
                content = {
                    val viewModel: AssignmentStudentViewModel by viewModels()
                    AssignmentScreen(navController = navController,role = Role.STUDENT, viewModel)
                }
            )
            composable(
                route = Screen.Notification.route,
                content = {
                    val viewModel: NotificationViewModel by viewModels()
                    NotificationScreen(navController = navController,viewModel)
                }
            )
            composable(
                route = Screen.Profile.route,
                content = {
                    val viewModel: StudentProfileViewModel by viewModels()
                    ProfileScreen(navController = navController , viewModel,Role.STUDENT)
                }
            )
        }
    }

    @Composable
    fun MangerScreens(navController: NavHostController){
        BasicScreen(
            screens = listOf(Screen.Home, Screen.UsersScreen(R.string.student,"students"), Screen.Notification, Screen.Profile),
            navController = navController
        ) {
            composable(
                route = Screen.Home.route,
                content = {
                    val viewModel: HomeMangerViewModel by viewModels()
                    HomeScreen(navController = navController,role = Role.MANGER, viewModel = viewModel)
                }
            )
            composable(
                route = Screen.UsersScreen(R.string.student,"students").route,
                content = { UsersScreen(navController = navController, usersType = Role.STUDENT) }
            )
            composable(
                route = Screen.Notification.route,
                content = {
                    val viewModel: NotificationViewModel by viewModels()
                    NotificationScreen(navController = navController, viewModel)
                }
            )
            composable(
                route = Screen.Profile.route,
                content = {
                    val viewModel: MangerProfileViewModel by viewModels()
                    ProfileScreen(navController = navController, viewModel, Role.MANGER)
                }
            )
        }
    }

    @Composable
    fun TeacherScreens(navController: NavHostController){
        BasicScreen(
            screens = listOf(Screen.Home, Screen.Assignment, Screen.Notification, Screen.Profile),
            navController = navController
        ) {
            composable(
                route = Screen.Home.route,
                content = {
                    val viewModel: HomeTeacherViewModel by viewModels()
                    HomeScreen(navController = navController, role = Role.TEACHER, viewModel = viewModel)
                }
            )
            composable(
                route = Screen.Assignment.route,
                content = {
                    val viewModel: AssignmentTeacherViewModel by viewModels()
                    AssignmentScreen(navController = navController, role = Role.TEACHER, viewModel)
                }
            )
            composable(
                route = Screen.Notification.route,
                content = {
                    val viewModel: NotificationViewModel by viewModels()
                    NotificationScreen(navController = navController,viewModel)
                }
            )
            composable(
                route = Screen.Profile.route,
                content = {
                    val viewModel: TeacherProfileViewModel by viewModels()
                    ProfileScreen(navController = navController, viewModel, Role.TEACHER)
                }
            )
        }
    }

    @Composable
    fun BasicScreen(
        screens: List<Screen>,
        navController: NavHostController,
        screenContent: NavGraphBuilder.() -> Unit
    ){
        Scaffold(
            bottomBar = {
                BottomNavigation(
                    contentColor = colorResource(id = R.color.brand_color),
                    backgroundColor = colorResource(id = R.color.background_color)
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    val currentDestination = navBackStackEntry?.destination
                    screens.forEach { screen ->
                        BottomNavigationItem(
                            icon = {
                                Icon(
                                    painter = painterResource(screen.icon),
                                    contentDescription = null // decorative element
                                ) },
                            label = { Text(stringResource(screen.resourceId)) },
                            selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                            onClick = {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        ) { innerPadding ->
            NavHost(
                navController,
                startDestination = Screen.Home.route,
                Modifier.padding(innerPadding),
                builder = screenContent
            )
        }
    }
    
}