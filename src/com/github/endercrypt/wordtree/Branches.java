package com.github.endercrypt.wordtree;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Branches extends BranchSource implements Serializable, Iterable<Branch>
{
	private static final long serialVersionUID = -2044895235264268617L;

	/**
	 * 
	 */

	private ArrayList<Branch> branches = new ArrayList<>();

	public Branches()
	{

	}

	public Branches(Branches other)
	{
		branches.addAll(other.branches);
	}

	public Branches(Branch[] branches)
	{
		add(branches);
	}

	public void add(Branch branch)
	{
		branches.add(branch);
	}

	public void add(Branches branches)
	{
		for (Branch other : branches.getSubBranches())
		{
			if (other != null)
				branches.add(other);
		}
	}

	public void add(Collection<? extends Branch> collection)
	{
		branches.addAll(collection);
	}

	public void add(Branch[] branches)
	{
		for (Branch branch : branches)
		{
			this.branches.add(branch);
		}
	}

	public int count()
	{
		return branches.size();
	}

	public Branch getBranch(int index)
	{
		return branches.get(index);
	}

	@Override
	public void getWords(Set<String> set, int limit)
	{
		for (Branch branch : branches)
		{
			int subLimit = limit - set.size();
			if (subLimit <= 0)
				return;
			branch.getWords(set, subLimit);
		}
	}

	@Override
	public Branch[] getBranches()
	{
		return branches.toArray(new Branch[branches.size()]);
	}

	@Override
	public Branch[] getSubBranches()
	{
		List<Branch> subBranches = new ArrayList<>();
		for (Branch branch : branches)
		{
			for (Branch subBranch : branch.getSubBranches())
			{
				if (subBranch != null)
				{
					subBranches.add(subBranch);
				}
			}
		}
		return subBranches.toArray(new Branch[subBranches.size()]);
	}

	@Override
	public Iterator<Branch> iterator()
	{
		return branches.iterator();
	}
}