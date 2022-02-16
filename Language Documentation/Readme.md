
# Language Documentation
What follows is the full explanation of how Opaciface operates, and how it interprets text as a program.
- Data Types and the Uberstack
- [Hiding Programs in English Text](#hiding-programs-in-english-text)
- Opcode Reference

## Hiding Programs in English Text
Opaciface begins interpretation by breaking down text into sentences. Sentences are delimited by their ending character, which may be a period (`.`), an exclamation point (`!`), or a question mark (`?`). If a sentence is still in progress (no ending character has been found) when the end of the file is reached, an error will be thrown.
- ### Why not interpret `EOF` as a sentence delimiter?
	The purpose of Opaciface is not to make programs that "look like" English text. The purpose of Opaciface is to make programs that are *indistiguishable* from English text. Simply put, sentences without ending punctuation are **ugly and wrong.** In an effort to enforce proper grammar, I decided that `EOF` will not be accepted as a sentence delimiter.
	- If you write your own Opaciface interpreter and change this rule, you will officially be designated as a **Dirttbagg** (technical term, spelled this way) until you repent of your ways.

There are two types of sentences in Opaciface. Any sentence ending in a period  (`.`) will be broken down further and interpreted as code. Any sentence ending in an exclamation point (`!`) or a question mark (`?`) will not be interpreted, but will instead be added in its entirety to the Uberstack as a new String cell.

Besides periods, exclamation points, and question marks, there is only one other symbol that Opaciface cares about: the comma (`,`). Commas are used in code sentences to turn on and off interpretation in the course of the sentence. As there is only one opcode for each individual operation, it may be necessary to insert several words into your text to make your operative words fit grammatically and coherently into the text. For this purpose, when a comma is reached in a sentence, all following words will be ignored by the interpreter until either another comma is reached or the sentence ends. By default, all sentences begin in word-interpretation mode, and are shifted into and out of word-ignoring mode by the use of commas.
- ### What if I want a sentence to begin in word-ignoring mode?
	You're in luck. There is a small set of words you can use to begin a sentence that will initiate word-ignoring mode right at the beginning of the sentence. All sentences that begin with the following words will begin in word-ignoring mode (until taken out of word-ignoring mode by a comma):
	- `But`, `Too`, `For`, `If`, and `As`.
