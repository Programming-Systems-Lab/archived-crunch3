#!/usr/bin/perl

##########################################################################
# Set report format - note that more than 20 characters in the word will
# get truncated in reporting, but not in word counting. So your final
# output may be confusing, but the counting should still work.

format REPORT_WORD =
@<<<<<<<<<<<<<<<<<<<< : @>>>>>> 
$word_read,             $num_times
.

##########################################################################
# Loop over input
    my $inputline;

%stoplist = ();
open(STOPLIST,  "stoplist.txt");

while (<STOPLIST>) {
    chomp;
    $stoplist{$_} = 1;
}

while(<>) {

    chomp;

    # if the line is blank, move on to the next line

    next if ($_ =~ /^$/);

    $inputline = $_;
    $inputline = lc($inputline);

    # Remove newlines, and replace punctuation with spaces. Add more
    # punctuation into the /PATTERN/ if and only if you know what you're
    # doing.

    $inputline =~ s/[\,\.\;\:\!\?\'\`\"\/\\\(\)\[\]\<\>]/ /g; 

    ## Split the line on spaces
    
    my @words = split(/\s+/, $inputline);

    # Increase word count if already known; add to word list if not.
    foreach $word (@words) {
	next if ($word eq "");
	next if (exists $stoplist{$word});
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

