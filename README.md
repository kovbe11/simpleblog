# Simple blog motor
This project was for an interview, initially done in 8 hours. After they went through the code I received feedback, which I created as issues on github.
After an additional 4-5 hours of work I fixed the problems with my code, which is the current version.

A quick summary of the project:

Entities:

- BlogPost
- Category
- Tag

Functionality specification:

- CRUD for BlogPosts and Categories
- Assigning categories to BlogPosts, and removing them
- Searching for BlogPosts by tags of categories
- Maximum 5 categories for BlogPosts

Misc:

- REST api, auth not needed
- Initially done in 8 hours


You can also find the actual specification in hungarian in the docs folder.



After completing the task, the main issues were the following:
- I gave too many responsibilities to the controller and the model layer
- I used a native query for the search functionality, which ended up to be much more complicated than necessary (also without countquery, it shouldn't work but it actually did somehow)

Some smaller pieces of advice worth mentioning:
- I used custom implementation for auditing: not a problem but using spring features is preferred
- Delivering less features well tested could have been better, than delivering all features without testing


Things to do:
- testing
- docs
