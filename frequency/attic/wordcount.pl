#!/usr/bin/perl

##########################################################################
# Usage : perl wordcount.pl < article.txt

format REPORT_WORD =
@<<<<<<<<<<<<<<<<<<<< : @>>>>>> 
$word_read,             $num_times
.

##########################################################################
# Loop over input
while(<>) {
  # Remove newlines, and replace punctuation with spaces. Add more
  # punctuation into the /PATTERN/ if and only if you know what you're
  # doing.
  chomp; 
  s/[,\.;:!?\'\`\"\/\\\(\)\[\]\<\>]/ /g; 

  # Change to lowercase, so that capitals don't confuse word count.
  $_ = lc;

  # change $_ into array of words called @_
  split;

  # Increase word count if already known; add to word list if not.
  foreach $word (@_) {
    ($wordlist{$word} > 0) && do {$wordlist{$word} += 1;};
    ($wordlist{$word} < 1) && do {$wordlist{$word} =  1;};
  }
}

##########################################################################
# Leave this line in...
@sort_word = keys %wordlist;

# ... And remove hashes from any one of these lines if you WANT sorting.
# Options are: sort by value;
#              reverse sort by value;
#              sort by word;
#              reverse sort by word.
# I haven't tested any of them properly!

# @sort_word = sort {$wordlist{$a} <=> $wordlist{$b}} @sort_word;
 @sort_word = sort {$wordlist{$b} <=> $wordlist{$a}} @sort_word;
# @sort_word = sort {$a cmp $b}                       @sort_word;
# @sort_word = sort {$b cmp $a}                       @sort_word;

##########################################################################
# Finally: write out the word list, using the previously defined format.

# Declare format
$~ = "REPORT_WORD";

# Loop over (possibly sorted) word list
foreach $i (@sort_word) { 
  $word_read = $i; 
  $num_times = $wordlist{$i}; 
  write;
}
##########################################################################
