# bitFlyerTestApp
Hello! Thank you for inviting me to do this coding test. I'll explain in short about this app and the approaches that I used.

I used a clean architecture approach with some simplifications. I didn't add interactors to the domain layer, because they would not give any advantage.
They would only proxy calls to repositories, so I decided to get rid of them and make things a little bit simpler.

I used the MVVM pattern because I consider it the most efficient and understandable pattern to use in Android development (we can mix it with MVI though).

Room is used for saving the list's state in case of process death (the list may become really big, so we do not want to use savedInstanceState). Process death
can be tested by invoking `adb shell am kill com.bitflyer.testapp` while the app is in the background (the stop button in the "run" tab in Android studio works differently).

The app is not optimized for landscape mode, but landscape mode is allowed.

Viewmodels are properly tested as well as repositories and mappers.

I'm not a designer, and I didn't have any additional spare time to make a great design, so the visual style of the app is quite simple.

# Possible enhancements that could be done to this app:
1. Offline first approach. We may use the google pagination library to fetch data to the database and use a database as the single source of truth.
2. If the application is planned to be big we may divide it into modules (e.g. by feature) to speed up build times. 
3. Add an accessibility feature to make the app available for users with disabilities.
4. Adapt the app for landscape mode. Add necessary insets, and show two fragments simultaneously on devices with wide screens (the left side is the list, and the right side is the details).
5. Add new features (browse repositories, show contribution statistics, etc., like the GitHub website itself).
6. Improve the design (UI/UX).
7. Test on different devices and API levels.
8. Write integration tests.
9. Add a subscription feature to make some money from it :)
