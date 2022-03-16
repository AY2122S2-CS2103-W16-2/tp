---
layout: page
title: uMessage (v1.2) User Guide
---

*uMessage* is a desktop app that helps university students manage contacts, academics and CCAs that communicate and hold meetings on various platforms. It is optimized for keyboard users.

* Table of Contents
{:toc}

--------------------------------------------------------------------------------------------------------------------

## Quick start

1. Ensure you have Java `11` or above installed in your Computer.

1. Download the latest `uMessage.jar` from

1. Copy the file to the folder you want to use as the _home folder_ for *uMessage*.

1. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app contains some sample data.<br>

1. Type the command in the command box and press Enter to execute it. e.g. typing **`help`** and pressing Enter will open the help window.<br>
   Some example commands you can try:

   * **`list`** : Lists all contacts.

<<<<<<< HEAD
   * **`add`**`n/Betsy Crowe d/Phone, 99900099 d/Email, e0123456@gmail.com`: Adds a contact named `Betsy Crowe` to the Address Book.
=======
   * **`add`**`n/Betsy Crowe d/Phone, 99900099 d/Email, e0123456@gmail.com` : Adds a contact named `Betsy Crowe` to the Address Book.
>>>>>>> 56b8f95b3b6363d4ef8b70bec8e31b59d2b984b3

   * `alex` or **`find`**`alex` : Finds the name "Alex" in the list

   * **`delete`**`alex` : Deletes Alex if the person exists.

   * **`clear`** : Deletes all contacts.

   * **`exit`** : Exits the app.

1. Refer to the [Features](#features) below for details of each command.

--------------------------------------------------------------------------------------------------------------------

## Features

<div markdown="block" class="alert alert-info">

**:information_source: Notes about the command format:**<br>

* Words in `UPPER_CASE` are the parameters to be supplied by the user.<br>
  e.g. in `add n/NAME`, `NAME` is a parameter which can be used as `add n/John Doe`.

* Items in square brackets are optional.<br>
  e.g `n/NAME [t/TAG]` can be used as `n/John Doe t/friend` or as `n/John Doe`.

* Items with `…`​ after them can be used multiple times including zero times.<br>
  e.g. `[t/TAG]…​` can be used as ` ` (i.e. 0 times), `t/friend`, `t/friend t/family` etc.

* Parameters can be in any order.<br>
  e.g. if the command specifies `n/NAME t/TAG`, `t/TAG n/NAME` is also acceptable.

* If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of the parameter will be taken.<br>
  e.g. if you specify `n/John n/Joe`, only `n/Joe` will be taken.

* Extraneous parameters for commands that do not take in parameters (such as `help`, `list`, `exit` and `clear`) will be ignored.<br>
  e.g. if the command specifies `help 123`, it will be interpreted as `help`.

</div>

### Viewing help : `help`

Shows a message explaning how to access the help page.

![help message](images/helpMessage.png)

Format: `help`


### Adding: `add`

Adds either: 
  1. a person, or 
  2. a new tag to an existing person.

##### Format:
**1. Adding a person:**
  - `add n/NAME [d/{SOCIAL MEDIA PLATFORM}, {ID}]… [t/TAG]…`

_Tip: A person can have one or more details_
_Tip: A person can have zero or more tags_

**Examples:**

> `add n/John Doe d/Telegram, johndoe`
> `add n/Betsy Crowe d/Phone, 99900099 d/Email, e0123456@gmail.com`
> `add n/Potter d/Phone, 49502583 t/cs2030`

**2. Adding new tag to a person:**
  - `add NAME t/NEWTAG`
  - `add INDEX t/NEWTAG`

_Tip: Specify the person that you want to add the tag to by using their full name or their index in the contact list_

**Examples:**

> `add John Doe t/friend`
> `add 2 t/friend`

### Copying a person : `copy`

**Format:** `copy NAME`

Deletes the person with the specified NAME.
NAME is insensitive (like find)

**Examples:**

`copy David Li`
`copy Bernice Yu`

### Listing all persons : `list`

Shows a list of all persons in the address book.

Format: `list`

### Locating persons by name : `[find]`

Finds persons whose name contain any of the given keywords.

**Format:**  `[find] KEYWORD [MORE_KEYWORDS]`

The search is case-insensitive. e.g `hans` will match `Hans`
The order of the keywords does not matter. e.g. `Hans Bo` will match `Bo Hans`
Only the name is searched.
Only full words will be matched e.g. `Han` will not match `Hans`
Persons matching at least one keyword will be returned (i.e. OR search). e.g. `Hans Bo` will return `Hans Gruber`, `Bo Yang`
The search will attempt to search all fields and tags to return a match.

**Examples:**

`find kaaviya` (finds the name "Kaaviya")
`Kaaviya` (does the same as above)

### Deletion : `delete`

Deletes either: 
  1. a person, or 
  2. a tag from an existing person.

##### Format:
**1. Deleting Person**
Deletes the person with the specified `NAME` or `INDEX`.
- `delete NAME`
- `delete INDEX`

**Examples:**
> delete kaaviya
> delete 1

**2. Deleting Tag from a Person**
Deletes tag `TAG` from a person specified using `NAME` or `INDEX`.
- `delete NAME t/TAG`
- `delete INDEX t/TAG`

_Tip: Name is case sensitive._
_Tip: The index must be a positive integer 1, 2, 3, ..._

**Examples:**
> delete kaaviya t/friend
> delete 1 t/colleague

### Clearing all entries : `clear`

Clears all entries from the address book.

Format: `clear`

### Exiting the program : `exit`

Exits the program.

Format: `exit`

### Saving the data

AddressBook data are saved in the hard disk automatically after any command that changes the data. There is no need to save manually.

### Editing the data file

AddressBook data are saved as a JSON file `[JAR file location]/data/addressbook.json`. Advanced users are welcome to update data directly by editing that data file.

<div markdown="span" class="alert alert-warning">:exclamation: **Caution:**
If your changes to the data file makes its format invalid, AddressBook will discard all data and start with an empty data file at the next run.
</div>


--------------------------------------------------------------------------------------------------------------------

## FAQ

**Q**: How do I transfer my data to another Computer?<br>
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains the data of your previous AddressBook home folder.

--------------------------------------------------------------------------------------------------------------------

## Command summary

Action | Format, Examples
--------|------------------
**Add** | `add n/NAME [d/{SOCIAL MEDIA PLATFORM}, {ID}]… [t/TAG]…​`<br> e.g., `add n/Betsy Crowe d/Phone, 99900099 d/Email, e0123456@gmail.com`
**Clear** | `clear`
**Copy**  | `copy NAME`
**Delete** | `delete NAME` or `delete NAME INDEX`<br> e.g., `delete Alex`, `delete Alex 2`
**Find** | `[find] KEYWORD [MORE_KEYWORDS]`<br> e.g., `find James Jake`, `James Jake`
**List** | `list`
**Help** | `help`
