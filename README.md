# Adrian's EasyShop
#### This Application utilizes Java Object Oriented Analysis and Design. Implements a simple user interface interactable by the command line in order to process user inputs. Processes and records sandwich creation plus a drink and chips. 

## Table of Contents

- [Overview](#Overview)
- [Features](#features)
- [Favorite Code Snippet](#favorite-code-snippet)
- [Build Process](#build-process)
- [Authors](#authors-)
- [Links](#links)

## Overview

![Diagram](Images/CapstoneDiagram.png)

![DeliProjectFavCode](Images/OrderMenu.png)

## Streamlined and simple user interface allows user to easily navigate application menus

## Features

* Creates a new sandwich according to the user's preferences and choices
* Efficiently lists ongoing user inputs for sandwich toppings, bread, etc.
* Writes and files a receipt of completed orders
* Calculates total price of orders 
* Allows users to navigate between menu prompts
* Updates Local Date and Time to implement onto receipts
* Prompts user on pre-made sandwiches to add to their order

## Favorite Code Snippet

![FavCode](Images/DeliProjectFavCode.png)
![LiveCurrentSandwich](Images/Live%20Current%20Sandwich%20Prompt.png)

For-Each loop utilized to iterate through long list of options instead of having an overly big amount of switch-cases

Method that optimally prompts user of what is in their current sandwich to create a better user experience

## Build Process

- Follow the [React Native Guide](https://facebook.github.io/react-native/docs/getting-started.html) for getting started building a project with native code. **A Mac is required if you wish to develop for iOS.**
- Clone or download the repo
- `yarn` to install dependencies
- `yarn run link` to link react-native dependencies
- `yarn start:ios` to start the packager and run the app in the iOS simulator (`yarn start:ios:logger` will boot the application with [redux-logger](<https://github.com/evgenyrodionov/redux-logger>))
- `yarn start:android` to start the packager and run the app in the Android device/emulator (`yarn start:android:logger` will boot the application with [redux-logger](https://github.com/evgenyrodionov/redux-logger))


## Authors 

Adrian San Miguel 

## Links

* [GitHub](https://github.com/asanmiguel12) 
* [LinkedIn](https://www.linkedin.com/in/adrianchristiansanmiguel/)
