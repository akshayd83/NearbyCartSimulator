### NearbyCartSimulator
Nearby Cart Simulator App
 
  **Splash Screen**
       
    It shows number of Carts between 5-10 on Splash Screen
 
  **Home Screen**
  
     Shows all the carts in View Pager with following information:
     - Cart Name
     - Cart Range
     - Cart Signal Strength
     - Indicator of Signal Strength
       
   **UI Components on Home Screen**
      * Displayes Circular Pager Indicator `(By Jake Wharton)`
      * FAB to allow user to re-order carts in certain order or in random order
      * Pull to refresh component: This will refresh the cart strength and update the carts
       

   **Cart Strength**
      1. Strength is between 1-100 and is categorized in weak, medium and strong
      2. Strength less than 30 is `weak`
      3. Strength greater than 30 but less than 60 is `medium`
      4. Strength greater than 60 is `strong`
    
   **Cart Order Screen**
     Default is random which orders the cart according to the ascending order of signal strength
     User can toggle between random and custom order
     Upon switching of the random order, user can re order the carts by drag and drop of list view elements
     
       
    
