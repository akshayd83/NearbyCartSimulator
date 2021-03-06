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
 
   Default is random which orders the cart according to the ascending order of signal strength.
    
   User can toggle between random and custom order.
   
   Upon switching of the random order, user can re order the carts by drag and drop of list view elements.


**API:**
 
The application calls Random.org API as follows:
 *	It makes call to https://www.random.org/integers/?num=1&min=1&max=100&col=1&base=10&format=plain&rnd=new in order get random strength.
 *	The API calls are made Using Android AsyncTask Executor to do the parallel execution. The total number of tasks executed is equal to the number of carts.
 *	For each task 3 API calls are made in order to simulate an average strength of the cart.
 *	The Min and Max are 1 and 100 respectively



**Libraries: This project uses 5 Libraries**

1)	DragListView Library: https://github.com/woxthebox/DragListView to allow drag and drop

2)	CircularPager Indicator by Jake Wharton: https://github.com/JakeWharton/ViewPagerIndicator

3)	Android Material Refresh Library for Pull to refresh: https://github.com/android-cjj/Android-MaterialRefreshLayout

4) Butterknife by `Square` for UI Dependency Injection

5) Glide by Google to load images.

    
