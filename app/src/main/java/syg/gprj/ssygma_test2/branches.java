package syg.gprj.ssygma_test2;

public class branches
{
    private String branch_name;
    private String branch_location;

    public branches() {}

    public branches (String branch_name, String branch_location)
    {
        this.branch_name=branch_name;
        this.branch_location=branch_location;
    }

    public String getBranch_name()
    {
        return branch_name;
    }

    public void setBranch_name(String branch_name)
    {
        this.branch_name = branch_name;
    }

    public String getBranch_location()
    {
        return branch_location;
    }

    public void setBranch_location(String branch_location)
    {
        this.branch_location = branch_location;
    }
}