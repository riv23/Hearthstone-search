Hearthstone Search Engine
Search engine to quickly and easily seach Hearthstone game cards.

Cards are automatically retrieved through the API [hearthstonejson](http://hearthstonejson.com).

All cards (text and images ) are the intellectual property of Blizzard Inc.

## Use API

The API is very simple to use.
The pattern of the url is: <base_url>/api/search?q=<your_query>, replace <your_query> by a Google search api valid request.

## Run local

To start the app, use the [App Engine Maven Plugin](http://code.google.com/p/appengine-maven-plugin/) that is already included in this demo.  Just run the command.

    mvn appengine:devserver
