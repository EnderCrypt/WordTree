package com.github.endercrypt.wordtree.interpret;

import java.util.List;

import com.github.endercrypt.wordtree.BranchSource;
import com.github.endercrypt.wordtree.Branches;
import com.github.endercrypt.wordtree.pattern.BranchPattern;

public class CompiledPattern
{
	private BranchPattern[] patterns;

	public CompiledPattern(List<BranchPattern> patterns)
	{
		this(patterns.toArray(new BranchPattern[patterns.size()]));
	}

	public CompiledPattern(BranchPattern[] patterns)
	{
		this.patterns = patterns;
	}

	public Branches run(BranchSource source)
	{
		Branches branches = new Branches(source.getBranches());
		for (BranchPattern pattern : patterns)
		{
			branches = branches.get(pattern);
		}
		return branches;
	}
}
