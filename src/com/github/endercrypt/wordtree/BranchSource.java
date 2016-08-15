package com.github.endercrypt.wordtree;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.github.endercrypt.wordtree.exception.WordTreeException;
import com.github.endercrypt.wordtree.pattern.Any;
import com.github.endercrypt.wordtree.pattern.Letter;
import com.github.endercrypt.wordtree.pattern.Range;
import com.github.endercrypt.wordtree.pattern.Select;

public abstract class BranchSource
{
	private Branches get(Branch[] subBranches, BranchPattern pattern)
	{
		Branches resultBranches = new Branches();
		for (Branch branch : subBranches)
		{
			if (pattern.doesAllow(branch.getChar()))
			{
				resultBranches.add(branch);
			}
		}
		return resultBranches;
	}

	public Branches get(BranchPattern... patterns)
	{
		return get(getSubBranches(), patterns);
	}

	private Branches get(Branch[] subBranches, BranchPattern... patterns)
	{
		BranchPattern pattern = patterns[0];
		Branches branches = new Branches(get(subBranches, pattern));
		if (patterns.length > 1)
		{
			patterns = Arrays.copyOfRange(patterns, 1, patterns.length);
			return get(branches.getSubBranches(), patterns);
		}
		else
		{
			return branches;
		}
	}

	public Branch get(char... chars)
	{
		return get(getSubBranches(), chars);
	}

	private Branch get(Branch[] subBranches, char... chars)
	{
		char c = chars[0];
		Branch branch = new Branches(get(subBranches, new Letter(c))).getBranch(0);
		if (chars.length > 1)
		{
			chars = Arrays.copyOfRange(chars, 1, chars.length);
			return get(branch.getSubBranches(), chars);
		}
		else
		{
			return branch;
		}
	}

	public Branch get(String word)
	{
		return get(word.toLowerCase().toCharArray());
	}

	public Branches getByPattern(String patternString)
	{
		Branches branches = new Branches(getBranches());
		int i = 0;
		while (i < patternString.length())
		{
			char c = patternString.charAt(i);
			i++;
			if (WordTree.isLetter(c)) // LETTER
			{
				branches = branches.get(new Letter(c));
				continue;
			}
			if (c == '?') // ANY
			{
				branches = branches.get(new Any());
				continue;
			}
			if (c == '[') // SELECT
			{
				int to = patternString.indexOf(']', i);
				branches = branches.get(new Select(patternString.substring(i, to)));
				i = to;
				continue;
			}
			if (c == '(') // RANGE
			{
				char start = patternString.charAt(i);
				if (patternString.charAt(i + 1) != '-')
					throw new WordTreeException("getByPattern ( RANGE ) requires - between, eg (a-c)");
				char stop = patternString.charAt(i + 2);
				branches = branches.get(new Range(start, stop));
				i += 2;
				continue;
			}
		}
		return branches;
	}

	public Set<String> getWords()
	{
		return getWords(-1);
	}

	public void getWords(Set<String> set)
	{
		getWords(set, -1);
	}

	public Set<String> getWords(int limit)
	{
		Set<String> words = new HashSet<>();
		getWords(words, limit);
		return words;
	}

	public abstract void getWords(Set<String> set, int limit);

	public abstract Branch[] getBranches();

	public abstract Branch[] getSubBranches();
}
