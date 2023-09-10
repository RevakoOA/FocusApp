### Status feature module
As all feature modules this module contains UI logic.
Usually in form of Route + Screen + ViewModel.

#### Routes
Routes aggregated in the app module which allows to navigate between different screens.
Each Route works as public API for the corresponding Screen.

#### Screen
Screen implementation that contains UI to show based on provided data.

#### ViewModel
Regular ViewModel that collect data from repositories and provides that to the Screen inside Route.
