[![Build Status](https://travis-ci.org/GeminiCode/Hearthstone-search-api.svg?branch=master)](https://travis-ci.org/GeminiCode/Hearthstone-search-api)

## Hearthstone search api
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

### v1.3.1
* Removed idle instance (so expansive xD).
* Added form contact.
* Added some coverage tests.
* Added Java Bundle Resource instead of huge Java class.
* Change cron schedule (every days -> every months).
* Fix a lot of bugs.

### v1.2.2
* Added a lot of refactoring.
* Fix bugs.
* Mana filters are on front side now.
* Heroes are not indexed now.
* Fix search with ' char.
* Optimize indexing, the number of requests is optimized now.

### v1.2.1
* Mana filter improvement.

### v1.2.0
* Added optional fiter on mana value.

### v1.1.0
* URL can be shared now. `/q=stormwind&lang=en` diplays automatically "stormwind" cards in English. 
* Added link to the GitHub page.
* Added expansion pack value to each card.
* Added Blackrock mountain cards.
* Fix diplay order.
* Added i18n support (French and English).

### v1.0.0
* Inital release.
