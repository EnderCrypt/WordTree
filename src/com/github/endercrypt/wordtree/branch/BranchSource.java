package com.github.endercrypt.wordtree.branch;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import com.github.endercrypt.wordtree.pattern.BranchPattern;
import com.github.endercrypt.wordtree.pattern.Letter;

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
