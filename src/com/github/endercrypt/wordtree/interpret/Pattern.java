package com.github.endercrypt.wordtree.interpret;

import java.util.List;

import com.github.endercrypt.wordtree.branch.BranchSource;
import com.github.endercrypt.wordtree.branch.Branches;
import com.github.endercrypt.wordtree.pattern.BranchPattern;

public class Pattern
{
	private BranchPattern[] patterns;

	public Pattern(List<BranchPattern> patterns)
	{
		this(patterns.toArray(new BranchPattern[patterns.size()]));
	}

	public Pattern(BranchPattern[] patterns)
	{
		this.patterns = patterns;
	}

	public Branches process(BranchSource source)
	{
		Branches branches = new Branches(source.getBranches());
		for (BranchPattern pattern : patterns)
		{
			branches = branches.get(pattern);
		}
		return branches;
	}
}
