# Language Documentation

Opaciface is a stack-based (sort-of) esoteric programming language designed to allow programmers to hide programs within passages of English text. What follows is the full explanation of how Opaciface operates, and how it interprets text as a program.

1. [Memory and Data Types](#memory-and-data-types)
    - [The Stackiplex](#the-stackiplex)
    - [Integers](#integers)
    - [Floats](#floats)
    - [Strings](#strings)
1. [Hiding Programs in English Text](#hiding-programs-in-english-text)
    - [Sentences](#sentences)
    - [Commas](#commas)
    - [Consonants and Vowels](#consonants-and-vowels)
1. Opcode Reference

## Memory and Data Types

As are most esoteric programming languages with a finite instruction set, Opaciface is stack-based. However, to make programming in Opaciface easier and more fun, I went more for "stack-based-with-tricks" and ended up creating the Stackiplex.

### The Stackiplex

All data in Opaciface is stored on the Stackiplex, an arbitrarily-sized list of cells that store the different data types that the language supports. You can push/pop on top of the Stackiplex, you can shove/scoop on the bottom of the Stackiplex, and you can even insert, delete, or rearrange items at arbitrary locations in the Stackiplex.

- #### Doesn't that make it a deque, not a stack?
  No, deques can only be accessed at the ends. You can address arbitrary locations in the Stackiplex.
- #### It's definitely more capable than a classic stack. Why call it a stack?
  Both "Opaciface" and "Stackiplex" were the result of several factors:
    1. I wanted something that sounded decently cool. Here you go.
    1. Linguistic remixing is a hobby of mine. "Opaciface" began with me thinking about the word "hide," which became "obscure," which became "opaque," which became "opacify."
    1. I ran through a bunch of different half-nonsense words in my head related to the root words/concepts I needed to convey in naming. Every time I came up with a new candidate, I Googled the word, in quotes, to see if I could find any matches. I didn't stop rehashing and remashing my candidates until Google returned 0 results.
        - You'd be surprised how difficult it is to come up with new words that have 0 results on Google! If you're looking for a fun creative challenge, try it some time.

The Stackiplex stores different data types in variably-sized cells for ease of use. The more programmers can modify data at a high level (rather than having to deal with each character of a string being stored individually on a stack, for example), the less code anyone has to write to accomplish a task, allowing for easier and more flexible hiding of code within text.

### Integers
Integer cells contain arbitrary-precision/automatically-growing signed integers. I decided that for the sake of making math as easy as possible for programmers to handle, arbitrary-precision integers would be used in place of fixed-size integers.

### Floats
Similarly to Integer cells, Float cells contain arbitrary-precision/automatically-growing floating point numbers. The choice to use arbitrary precision, once once again, was to make math as friendly as possible.

### Strings
String cells contain Strings. Strings can be arbitrarily long, or however long the underlying language behind your Opaciface interpreter supports. I decided to make the Stackiplex capable of storing a whole String in a single cell (rather than most esolangs' habit of storing Strings character-by-character on a stack) to make complex String manipulations possible in fewer operations.

## Hiding Programs in English Text

### Sentences
Opaciface begins interpretation by breaking down text into sentences. Sentences are delimited by their ending character, which may be a period (`.`), an exclamation point (`!`), or a question mark (`?`). If a sentence is still in progress (no ending character has been found) when the end of the file is reached, an error will be thrown.

- #### Why not interpret `EOF` as a sentence delimiter?
  The purpose of Opaciface is not to make programs that "look like" English text. The purpose of Opaciface is to make programs that are *indistiguishable* from English text. Simply put, sentences without ending punctuation are **ugly and wrong.** In an effort to enforce proper grammar, I decided that `EOF` will not be accepted as a sentence delimiter.
  - If you write your own Opaciface interpreter and change this rule, you will officially be designated as a **Durtbagg** (technical term, spelled this way) until you repent of your ways.

There are two types of sentences in Opaciface. Any sentence ending in a period  (`.`) will be broken down further and interpreted as code. Any sentence ending in an exclamation point (`!`) or a question mark (`?`) will not be interpreted, but will instead be added in its entirety to the Stackiplex as a new String cell.

### Commas
Besides periods, exclamation points, and question marks, there is only one other symbol that Opaciface cares about: the comma (`,`). Commas are used in code sentences to turn on and off interpretation in the course of the sentence. As there is only one opcode for each individual operation, it may be necessary to insert several words into your text to make your operative words fit grammatically and coherently into the text. For this purpose, when a comma is reached in a sentence, all following words will be ignored by the interpreter until either another comma is reached or the sentence ends. By default, all sentences begin in word-interpretation mode, and are shifted into and out of word-ignoring mode by the use of commas.

- #### What if I want a sentence to begin in word-ignoring mode?
    You're in luck. There is a small set of words you can use to begin a sentence that will initiate word-ignoring mode right at the beginning of the sentence. All sentences that begin with the following words will begin in word-ignoring mode (until taken out of word-ignoring mode by a comma):
  - `But`, `Too`, `For`, `If`, and `As`

### Consonants and Vowels
After figuring out what sentences to interpret and tossing out the words marked for ignorance, each word that remains is interpreted one-by-one, in written order. The first thing Opaciface does to a word is reduce it to its first three consonants. All vowels and non-letters are ignored, and letter repetition is allowed. The three-consonant groups resulting from this process form the opcodes for all instructions in Opaciface.

For clarity, here are a few examples of words and their reduced form:
- `natural` 🠖 `ntr`
- `entirely` 🠖 `ntr`
- `eating` 🠖 `tng`
- `tangentially` 🠖 `tng`
- `ballast` 🠖 `bll`
- `eyeball` 🠖 `bll`
  - Consonants are defined as all letters which are not vowels. Vowels are defined in Opaciface as `a`, `e`, `i`, `o`, `u`, and `y`.

It should be noted that the words in a sentence are consonant-reduced one at a time, and not all at once, for a good reason: some opcodes may modify the way the words that follow it are treated.