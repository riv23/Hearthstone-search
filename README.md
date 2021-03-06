[![Build Status](https://travis-ci.org/GeminiCode/Hearthstone-search.svg?branch=master)](https://travis-ci.org/GeminiCode/Hearthstone-search-api)

## Hearthstone search api
Hearthstone Search is a search engine that allows to retrieve the cards from HearthStone easily and instantly, [Demo !](http://hearthstone-search.com/)

Please note : 
* Cards are automatically retrieved through the API [hearthstonejson](http://hearthstonejson.com).
* All cards (text and images) are the property of Blizzard Inc.

## Use API

* The API is very simple to use !
* The url pattern is: `/api/search?q=`, `q` is a valid Google Search Api request.
* `lang` param allows to select the language. The Default Language is English (Only French and English are supported for the moment).
* Auto completion api use the following url : `/api/names?q=le`, this example show all cards (or attributes) starting with : "len" (ex : Leeroy Jenkins, Legendary, Leper Gnome).
* The API can be used [here](http://hearthstone-search.com).


## Run local

### Prerequisites :

To start the app, use the [App Engine Maven Plugin](http://code.google.com/p/appengine-maven-plugin/) that is already included in this project. Just run the command.

    mvn appengine:devserver

### Your own instance :

Create your own instance appengene with Hearthstone-search :
* Create your awn project [here](https://appengine.google.com/). 
* Change `src/main/webapp/WEB-INF/appengine-web.xml` (replace `hearthstone-search-cards` with your own ID).
* Change `pom.xml` (remove or comment line 148 to 158).
* Execute the following command : `mvn clean install appengine:update`.

### How cards are checked ? 

The following url /check launch cards indexing. Each call remove all cards from the index and refresh the index with the news cards from [hearthstonejson](http://hearthstonejson.com).
    
## Want to collaborate ? 

Pull request are welcome :)

## Change log

[Release note page](https://github.com/GeminiCode/Hearthstone-search/releases)

