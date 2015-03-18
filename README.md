[![Build Status](https://travis-ci.org/GeminiCode/Hearthstone-Search-Engine.svg?branch=master)](https://travis-ci.org/GeminiCode/Hearthstone-Search-Engine)

## Hearthstone Search Engine
Hearthstone Search is a search engine that allows to retrieve the cards from HearthStone easily and instantly.

Please note : 
* Cards are automatically retrieved through the API [hearthstonejson](http://hearthstonejson.com).
* All cards (text and images) are the property of Blizzard Inc.

## Use API

* The API is very simple to use.
* The url pattern is: `/api/search?q=`, `q` is a valid Google Search Api request.
* The API can be used [here](http://hearthstone-search-cards.appspot.com).


## Run local

To start the app, use the [App Engine Maven Plugin](http://code.google.com/p/appengine-maven-plugin/) that is already included in this demo.  Just run the command.

    mvn appengine:devserver
    
## Want to collaborate ? 

Pull request are welcome :)
