# Water Bear Adventures

 The application will be a simple adventure game where the player 
 plays as a water bear (tardigrade). The tardigrade goes into different environments,
 such as space, and finds a treasure, finding food and escaping predators along the way.
     
## Phase 4: Task 2 - Testing and designing a robust class

Testing and design a class in the model package that is robust:

The robust class is the WaterBear class, and the methods I have made robust are:
    - runAway()
    - goUp()
    - goDown()
    - goRight()
    - goLeft()
    
## Phase 4: Task 3 - Refactoring notes

   - There is a lot of code duplication between the WaterBearAdventuresConsoleApp and the WaterBearAdventuresGraphicApp.
    I would like to create an abstract class in the ui package, called WaterBearAdventuresApp, which could contain the 
    methods that have duplicated code between the two classes, such as loadWaterBear() and saveWaterBear().
    WaterBearAdventuresConsoleApp and WaterBearAdventuresGraphicApp could then extend WaterBearAdventuresApp to 
    reduce code duplication.
    
   - WaterBearAdventuresConsoleApp and WaterBearAdventuresGraphicApp contain functions such as zeroZeroButtons(), 
   zeroOneButtons (in WaterBearAdventuresGraphicApp) or zeroZeroCommmands(), zeroOne commands 
   (in WaterBearAdventuresConsoleApp) that contain instructions on actions to take when the water bear's coordinates are
   equal to (0,0), (0,1) and so on. This structure leads to a lot of code duplication, and it can also lead to 
   inefficiency in extending the code, e.g. if I wanted to add more places the water bear could go to. To solve these 
   problems, I would like to create an AdventureMap class that contains a map of Place. The map could have all 
   the possible places the water bear could go to, with information like availability of treasure or food as values 
   corresponding to the appropriate Place as the key. The WaterBearAdventuresGraphicApp and 
   WaterBearAdventuresConsoleApp could then instantiate an Adventure map and add the Places and items as desired. This
   could simplify some of the code in the ui classes. If, in the future, functionality was to be added so that different
   types of adventures could be chosen (e.g. the user could choose to go on a 'Space adventure' or 'Sea adventure'), 
   this structure would allow for easily instantiating more maps for the water bear to explore in. 
  
   




