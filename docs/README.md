# Aurora User Guide

Aurora is a task management chatbot with 4 types of tasks, `todo`, `deadline`, `event` and `doWithinPeriod`, best suited for users who prefer typing commands!

![Gallery](./Ui.png)

## Installation:

1. Download the jar from [Aurora](https://github.com/ToxOptimism/ip/releases/) releases.
2. Double click the jar file or open a terminal and cd to the directory Aurora is downloaded and run:

```bash
java -jar Aurora-v0.2.jar
```

## Aurora's suite of features:
Available commands are:

`todo`, `deadline`, `event`, `doWithinPeriod`, `list`, `find`, `delete`, `mark`, `unmark`, `bye`

## Adding todos

Adds a todo with a specified description to the task list.

Command format:

```text
todo <description>
```
Example:
```text
todo Write an agenda
```

Returns a response on the added todo task.

```
I've added this task:
[T][ ] Write an agenda
Now you have 1 tasks in the list!
```

## Adding deadlines

Adds a deadline with a specified description and by date to the task list. 

Command format:

```text
deadline <description> /by <byDatetime>
```

`byDateTime` must be in 24-hour `dd/mm/yyyy hhmm` format.

Example:

```text
deadline return book /by 2/12/2019 1800
```

Returns a response on the added deadline task.

```
I've added this task:
[D][ ] return book (by: Dec 2 2019 6:00pm)
Now you have 1 tasks in the list!
```

## Adding events

Adds an event with a specified description, from date time and to date time to the task list.

Command format:

```text
event <description> /from <fromDateTime> /to <toDateTime>
```

`fromDateTime` and `toDateTime` must be in 24-hour `dd/mm/yyyy hhmm` format.
The `/from` argument identifier must come before the `/to` argument identifier.

Example:

```text
event project meeting /from 2/12/2019 1800 /to 4/12/2019 0800
```

Returns a response on the added event task.

```
I've added this task:
[E][ ] project meeting (from: Dec 2 2019 6:00pm to: Dec 4 2019 8:00am)
Now you have 3 tasks in the list!
```

## Adding do within periods

Adds an do within period with a specified description, start period date time and end period date time to the task list.

Command format:

```text
doWithinPeriod <description> /start <startPeriodDateTime> /end <endPeriodDateTime>
```

`startPeriodDateTime` and `endPeriodDateTime` must be in 24-hour `dd/mm/yyyy hhmm` format.
The `/start` argument identifier must come before the `/end` argument identifier.

Example:

```text
doWithinPeriod make presentation slides /from 2/12/2019 1800 /to 4/12/2019 0800
```

Returns a response on the added do within period task.

```
I've added this task:
[P][ ] make presentation slides (from: Dec 2 2019 6:00pm to: Dec 4 2019 8:00am)
Now you have 3 tasks in the list!
```

## Listing all tasks

List all tasks have entered into the task list.

Command format:

```text
list
```

Returns a response displaying all your tasks.

```
These are 3 entries on your todo:
1. [T][ ] borrow book
2. [D][ ] return book (by: Dec 2 2019 6:00pm)
3. [E][ ] project meeting (from: Dec 2 2019 6:00pm to: Dec 4 2019 8:00am)
```

## Deleting a task

Removes a task from the task list using a `index` that corresponds to the task order of the `list` command.

Command format:

```text
delete <index>
```

Example:

```text
delete 1
```

Returns a response on deleting the task

```
I've removed this task:
[T][X] borrow book
Now you have 2 tasks in the list!
```

## Mark task as complete

Marks a task from the task list as complete using a `index` that corresponds to the task order of the `list` command.

Command format:

```text
mark <index>
```

Example:

```text
mark 1
```

Returns a response on the task marked as complete.

```
This task has been marked as done:
[T][X] borrow book
```

## Mark task as not complete

Marks a task from the task list as not complete using a `index` that corresponds to the task order of the `list` command.

Command format:

```text
unmark <index>
```

Example:

```text
unmark 1
```

Returns a response on the task marked as not complete.

```
This task has been marked as done:
[T][ ] borrow book
```

## Finding a task

Find all tasks containing a specified keyword in its description.

Command format:

```text
find <keyword>
```

Example:

```text
find boo
```

Returns a response displaying tasks that matches the specified keyword.

```
1. [D][ ] return book (by: Dec 2 2019 6:00pm)
```

## Exiting

Exits the program.

Command format:

```text
bye
```

Returns a response displaying the goodbye message.

```
Bye. Hope to see you again soon!
```
