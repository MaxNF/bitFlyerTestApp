# bitFlyerTestApp
Hello! Thank you for inviting me to do this coding test. I'll explain in short about this app and the approaches that I used.

I used a clean architecture approach with some simplifications. I didn't add interactors to the domain layer, because they
would not give any advantage.
They would only proxy calls to repositories, so I decided to get rid of them and make things a little bit simpler.

I used the MVVM pattern because I consider it the most efficient and understandable pattern to use in Android development (we
can mix it with MVI though).

Room is used for saving the list's state in case of process death (the list may become really big, so we do not want to use
savedInstanceState). Process death
can be tested by invoking `adb shell am kill com.bitflyer.testapp` while the app is in the background (the stop button in the "
run" tab in Android studio works differently).

The app is not optimized for landscape mode, but landscape mode is allowed.

Viewmodels are properly tested as well as repositories and mappers.

I'm not a designer, and I didn't have any additional spare time to make a great design, so the visual style of the app is quite
simple.

# Possible enhancements that could be done to this app:
1. Offline first approach. We may use the google pagination library to fetch data to the database and use a database as the
   single source of truth.
2. If the application is planned to be big we may divide it into modules (e.g. by feature) to speed up build times.
3. Add an accessibility feature to make the app available for users with disabilities.
4. Adapt the app for landscape mode. Add necessary insets, and show two fragments simultaneously on devices with wide screens (
   the left side is the list, and the right side is the details).
5. Add new features (browse repositories, show contribution statistics, etc., like the GitHub website itself).
6. Improve the design (UI/UX).
7. Test on different devices and API levels.
8. Write integration tests.
9. Add a subscription feature to make some money from it :)

## Changelog (2023.02.05)

1. UserBriefEntity and its mapper moved to data package
2. Created UseCases as a part of domain layer. Created unit tests for these UseCases
3. UserListViewModel now appropriately depends on UseCases, not on api, repo, etc.
4. Fixed crash on the details screen
5. Updated some libraries versions

### There are several things about these changes I want to explain:

I fixed all things we discussed in the interview but there are still some violations of a strict clean architecture. These
violations are intentional and they are done by Google itself (see architecture
samples https://github.com/android/architecture-samples )

The domain layer knows about the "CallResult" class which contains the result of the network call or the error data. It is
possible to get rid of this class and surround the code inside ViewModel with try-catch blocks. But it will look ugly and
verbose, so I decided to leave the standard approach which is recommended by Google in its architecture samples. I think that
the final decision on what should be used in a real project (try-catch in viewmodels or CallResult) depends on a particular
team and there is no 100% right answer to this.

I created UseCases but didn't create separate domain models (entities). This is also a violation of the strict Clean
Architecture approach (introduced by Robert Martin aka "Uncle Bob"), but is acceptable and done by Google in its architecture
samples and architecture articles. For example check here https://developer.android.com/topic/architecture#domain-layer
Creating additional domain entities will force us to create new mappers (from DTO objects to domain entities), and it will lead
to writing a lot of boilerplate code with no benefits (I'm talking about this particular project.
The situation in another project may be different and this layer may be required.)

A separate local DTO object "UserBriefEntity" is required for maintainability. Google in its samples doesn't separate local
entities and network DTOs. I don't like this approach, because in real projects the JSON schema of a particular response may be
modified at any time in the future. Using a single DTO for local storage and a network response
will make us change database structure if JSON schema is changed on a backend service. This may lead to even more severe
consequences if the same DTO is used as a model for the UI layer. To eliminate bad consequences and have more control we can
use separate models for network response and local storage.
Now the whole flow is the following:
`Receive JSON Response -> convert to DTO object -> convert to local entity -> save in a database if required -> convert to a UI
model -> show data on a screen`
Conversion between models is done by mappers. In case of changing JSON schema on a backend service, we can just change DTO
class and the mapping logic without touching other code.