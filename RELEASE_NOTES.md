#Release Notes
## 1.2.0
* Added /status admin command. Extended station parameters.

## 1.1.1
* Fixed github Maven CI workflow.

## 1.1.0
* Fixed correct implementation of Selenium RemoteWebDriver in Docker.

## 1.0.0
Implemented all the logic, planned up to MVP:
*   User can subscribe on station monitoring
*   User can view list of station subscriptions on which user subscribes
*   User can unsubscribe from station monitoring
*   User can set an inactive bot and do not receive notifications
*   User can restart getting notifications
*   Admin has ability to see bot statistics

## 0.8.1-SNAPSHOT
*   Extend bot statistics for admins.

## 0.8.0-SNAPSHOT
*   Added AdminCommand annotation and AdminHelpCommand.

## 0.7.0-SNAPSHOT
*   Implement the scheduling of checking the status of the stations.
*   Added subscribers' notification about changing station statuses.

## 0.6.0-SNAPSHOT
*   Added the ability to delete station subscription.

## 0.5.0-SNAPSHOT
*   Added the ability to see a list of subscriptions.
*   Added the ability to subscribe on station monitoring.

## 0.4.0-SNAPSHOT
*   Added repository layer.

## 0.3.0-SNAPSHOT
*   Added deployment process to the project.

## 0.2.0-SNAPSHOT
*   Implemented Command pattern for handling Telegram Bo commands. Added commands: help, start, stop.
*   Added test for SendBotMessageService. Added tests that cover the implementation of the Command pattern.

## 0.1.0-SNAPSHOT
*   Added stub telegram bot.
*   Added SpringBoot skeleton project.
