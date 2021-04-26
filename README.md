# Foodbank UNICHEF

Google Solution Challenge 2021 (https://developers.google.com/community/dsc-solution-challenge)
Video Demo: https://youtu.be/b4j3L7hP-PI

*Team KJ: Dayoung Lena Kim, Ji-in Jeong*

## Project Description
More than 10 percent of the world’s population cannot meet their basic needs these days. The COVID-19 pandemic has been pushing more and more people in extreme poverty and the first increase in global poverty is expected to hit the world soon. This led to the increasing use of foodbanks, a free service to distribute food basics to people who do not have enough to eat. Today we can see food banks very easily in everyday life at community centres or grocery store entrances, which motivated our team to build a mobile application to help local communities, focusing on “no poverty”, “zero hunger” and “responsible consumption and production” of the United Nations’ sustainable development goals. 

The food bank UNICHEF mobile application promotes local food banks available for everyone and provides a more organized, convenient way to donate and receive food. It will increase food distribution in our communities and eventually help to reduce poverty. We also look forward to reducing food waste and environmental problems by making people donate food to a local food bank very easily through this app.

## Technologies
- Android Studio with Java
- Firebase Autentication & Real-time Database
- Google Cloud Storage
- QR Code & Google Maps API

## Architecture & Design
The application consists of two different major components: user and administrator. 
1. **User UI** has four pages: home, profile, QR code and maps. The landing page is home which has a food list showing food name, amount, expiry date and location. Once a user donates an item to the local food bank, it is saved on Firebase real-time database list and updates the list to every user. The same applies to recipients. It updates the recipient information of food data and does not show on the page if any item has been taken. The profile shows personal information stored in Firebase real-time database and bitmaps of personal QR codes are saved in Google cloud storage. The real-time database also contains information about food bank location. It shows not only the location on the food list of the main page but also labels on Google Maps to find the nearest location from the current location.

2. **Admin UI** has four different pages: home, user information, penalty management and maps. The landing page is the same as users and it shows the list of food, but the only difference is the administrator can see all the information even if items are expired or received. Admin can see users’ profile including user name, email and penalty information. This information is connected to the Firebase real-time database. The penalty page is separated from the users’ profile to make it more convenient to use. Once a specific user gets a penalty, it finds the user from Firebase real-time database using the email and increases the number of penalties. Maps with foodbank labels and login/logout have no difference from user UI.

The application has been implemented by 9 different activities.
**1. MainActivity**

![mainactivity](https://user-images.githubusercontent.com/51341750/114314028-0d610980-9b34-11eb-8849-12649e0c0151.PNG)

MainActivity has register button and sign-up button. User can register or login.

**2. LoginActivity**

User and Administrator have different ID and password so administrator can manage the app easily if they logins with admin ID and password. If administrator login with admin's ID, it goes to AdminActivity.

**3. UserActivity**

-Food list: Get the data from firebase real-time database in 'Food' field using listview.

-Side bar: It contains 4 lists using navigationview.

**4. AdminActivity**

-Food list, side bar: Same as above description of UserActivity.

**5. ProfileActivity**

It shows current user's profile. After comparing the current user's e-mail with e-mail in Firebase real-time database, the data of the person whose email matched is fetched and displayed as a profile.

**6. MemberActivity**

It shows user's profile by putting the data in Model.class and using adaptor with recyclerview.

**7. MapActivity**

It shows fridges' location using Google Map API.

**8. FoodActivity**

It shows informations of food using data from firebase real-time database. If user wants to take away the food, user clicks confirm buttons and the food is deleted from database. 

**9. Donation (DonateActivity)**

If user wants to donate the food, user have to write down food's information and it is added to 'Food' field in database.

## Testing Strategies
1. Sharing the rough ideas with other students who have technical backgrounds. 
   - This application was originally designed exclusively for users, donors and recipients, but there was a feedback saying that adding another authentication for administrators to manage food banks would be a great idea. 
   - **Solution**: Separate UI and account for users and administrators. The final application has two different landing pages. When logged in as admin, it shows a list of all the foods and members’ profile information while the user account shows a list of foods based on the location and their personal profile
 
 2. Asking potential app users with a wide range of ages about their concerns or suggestions after implementing basic functionalities, such as donating and receiving food items, labelling food banks on Google Maps API and managing users on admin accounts.
    - Users side: How trustful the app would be. They suggested that there should be an action in the case that someone puts bad or expired food. 
    - Admin side: How to increase the management efficiency. It would be hard to place at least one administrator at every single location. So, there should be a system to keep track of food flows.
    - **Solution**: Gave the administrators the rights to give penalties if needed. Anyone who got more than two penalties would not be allowed to use the food bank service anymore. Also, personal QR code per user has been added to help the management. People who want to either donate or take out food must scan their assigned QR code to access the food bank or open the fridge if they have one. It sends the food and user real-time information to the server and notifies the administrator, which doesn’t require in-person management to keep security.

## Next Steps for the Project
The application can be improved by using both modern technology and sustainable methods. As more and more local communities get to have food bank services, it might be hard to be managed by administrators. Using an environmental sensor to detect odors inside and foodbank and a surveillance camera to protect security will make remote management possible. Furthermore, sustainable solutions such as connecting solar panels to generate electricity for lights or refrigerators of food banks would be a next step for this project. Since a food bank is a non-profit organization, it will also encourage more people to use the services if it is operated by the government.
