[![Build Status](https://travis-ci.org/GeminiCode/Hearthstone-Search-Engine.svg?branch=master)](https://travis-ci.org/GeminiCode/Hearthstone-Search-Engine)

## Hearthstone Search Engine
Hearthstone Search is a search engine that allows to retrieve the cards from HearthStone easily and instantly, [Demo !](http://hearthstone-search-cards.appspot.com/)

Please note : 
* Cards are automatically retrieved through the API [hearthstonejson](http://hearthstonejson.com).
* All cards (text and images) are the property of Blizzard Inc.

## Use API

* The API is very simple to use !
* The url pattern is: `/api/search?q=`, `q` is a valid Google Search Api request.
* `lang` param allows to select the language. The Default Language is English (Only French and English are supported for the moment).
* The API can be used [here](http://hearthstone-search-cards.appspot.com).


## Run local

To start the app, use the [App Engine Maven Plugin](http://code.google.com/p/appengine-maven-plugin/) that is already included in this demo. Just run the command.

    mvn appengine:devserver
    
## Want to collaborate ? 

Pull request are welcome :)

## Change log

### v1.1.0
* URL can be shared now. `/q=stormwind&lang=en` diplays automatically "stormwind" cards in English. 
* Added link to the GitHub page.
* Added expansion pack value to each card.
* Added Blackrock mountain cards.
* Fix diplay order.
* Added i18n support (French and English).

### v1.0.0
* Inital release
