# The Brief:

Create a mini version of the Moneybox app that will allow existing users to login, check their account and add money to their moneybox.

## Part A - Fix current bugs

### Bug 1 - Layout does not look as expected

Solution: The right image 

I solved the layout issues by using constraint layout, and constraining the neccessery elements in a way that scaling for different screen sizes would behave in the correct manner. 

![](/images/correct_layout.png)

### Bug 2 - Validation is incorrect
Below is the following validation logic:

- Email is not optional and should match EMAIL_REGEX
- Password is not optional and should match PASSWORD_REGEX
- Name is optional, but if it contains any value it should match NAME_REGEX

Solution: 
I created a method inside of my presenter in charge of the validation logic. It makes a call to a method which will access the view interface implemented by the login fragment and return true if the input is valid. The logic is contained in nested if statements which will activate the neccessery error message in the view if the input is not valid.

The Regex is contained inside a companion object attached to the view. The main validation method will also activate a success or failure toast based on the outcome and will in the case of a success will make a call to a login method. This call is contained within a check for a network connection. See the code snippets below for reference.

I carried out testing using Mockito in which i mocked the view and stubbed the method returning the text from the view elements. I asserted for all combinations of valid and invalid entries for email, password and name based on pre-conceived scenarios. This let me know that the presenter was working to validate the entries.

### Bug 3 - TODO
Above the login button is an animation of an owl and a pig.  We would like this animation to play every time the user starts the activity and then loop indefinitely.  The logic for this animation should be as follows:

- The animation should start from frame **0** to **109** when the user first starts the activity.  See below for animation.
![](/images/firstpig.gif)
- When the first stage of the animation has finished it should then loop from frame **131** to **158** continuously.  See below for animation.<br/>
![](/images/secondpig.gif)


## Part B - Add 2 new screens 

## Project Breakdown

![](/images/wireframe.png)

This Project architecture:

- Dagger 2 (DI)

- Retrofit (REST)

- Butterknife (View Binding)

- RXKotlin (Reactive)

- Realm (Local Database/ Caching)

### Dependency Injection

I have used Dagger 2 to inject my presenters with the neccesserry singleton objects needed for making my network calls and working with the data returned. I have injected these presenters into the main activity for controlling fragment logic and my three fragments for controlling login/Account functionality.

I decided to keep the application to one activity with a fragment for each screen based on the relative simplistic functionality. This has enabled m to create a very fast and responsive application.

There are three dagger components: App, Activity and Fragment. The application component provides its same name module which contains a stack of @provides annotated methods culminating in an applicationModel instance which is my interactor for interfacing with my data. 

This interactor is injected with an API interface which contains all of my Retrofit RESTfull methods to make calls to the moneybox API. Retrofit will serialise the returned JSON to an object using GSON, and the reverse is carried out for making a payment in the Accounts screen.

The Activity module provided by my activity component simply provides the activity presenter and context. In this scope the only operations required involve fragment logic

The Fragment module provided by the fragmet subcomponent allows me to injects a presenter for each fragment which inherits the App components and its singletons. This ensures the fragments will receive their dependencies at creation and can carry out the required functionality in the correct scopes and with the neccesserry instance lifetimes.

### REST + RX

My RESTful network calls return SingleObservables, this pipeline is async in nature with the call being carried out on a background thread (observed on Schedulers.io io thread) and subscribed on the main thread: (the only thread that can update the ui). 

I have created one callback interface for making a payment (Compleatable) and one callback for updating user data (Single) this allowed me to conform the behaviour of consumtion of the data to my requirements.

The data is returned in a disposable, which is consumed by said callback which implements the matching interface (DisposableSingleObserver<Login_>()/DisposableCompletableObserver()).

### Persistence

For updating user data i am copying the de-serialised objects to realm on the mainthread,so that i can access and update the view from this managed object (Realm only allows creation and access from the same thread).

To update the profile with user data after it is available in realm i have implemented a realm listener in the mainActivity
which when triggered will activate and display the profile fragment. This profile fragment will onAttach retrieve the users data from realm and update the view. 

Deregistering the listener when its done to prevent unwanted behaviour. This prevents null pointers in my app and is a reactive design which only will show the profile and update the data when it becomes availabe.


### Screen 1 - Login screen

On the profile screen i have implemented the functionality based on the requirements.

- Display "Hello {name} **only** if they provided it on previous screen"
- Show the **'TotalPlanValue'** of a user.
- Show the accounts the user holds, e.g. ISA, GIA, LISA, Pension.
- Show all of those account's **'PlanValue'**.
- Shhow all of those account's **'Moneybox'** total.


### Screen 2 - User accounts screen

Also the user screen

- Show the **'Name'** of the account.
- Show the account's **'PlanValue'**.
- Show the accounts **'Moneybox'** total.
- Allow a user to add to a fixed value (e.g. £10) to their moneybox total.


## API Usage


#### Authentication
To login with this user to retrieve a bearer token you need to call `POST /users/login`.

Solution:
  @Headers("AppId:3a97b932a9d449c981b595", "Content-Type:application/json", "appVersion:5.10.0", "apiVersion:3.0.0")
    @POST("users/login")
    fun Login(@Body login: Login): Single<UserToken>
 
 

#### Investor Products
Provides product and account information for a user that will be needed for the two additional screens.

### One off payments
Adds a one off amount to the users moneybox.
```

