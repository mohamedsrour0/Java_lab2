enum Decision {
    APPROVED,
    DENIED
}
class ExpenseRequest {
    private final double amount;
    private final String purpose;
    ExpenseRequest(double amount, String purpose) {
        this.amount = amount;
        this.purpose = purpose;
    }
    public double getAmount() {
        return amount;
    }
    public String getPurpose() {
        return purpose;
    }
}
abstract class Approver {
    private final double limit;
    private final Approver next;
    Approver(double limit, Approver next) {
        this.limit = limit;
        this.next = next;
    }
    public Decision approve(ExpenseRequest request) {
        if (request.getAmount() <= limit) {

            return Decision.APPROVED;
        }
        if (next != null) {

            return next.approve(request);
        }
        return Decision.DENIED;
    }
}
class TeamLead extends Approver {
    TeamLead(Approver next) {
        super(1000, next);
    }
}
class Manager extends Approver {
    Manager(Approver next) {
        super(5000, next);
    }
}
class Director extends Approver {

    Director(Approver next) {
        super(50000, next);
    }
}
class CFO extends Approver {

    CFO(Approver next) {
        super(200000, next);
    }
}
class FallbackApprover extends Approver {

    FallbackApprover() {
        super(0, null);
    }
    @Override
    public Decision approve(ExpenseRequest request) {

        System.out.println(
                "Request could not be approved: "
                + request.getPurpose()
                + " - Amount: "
                + request.getAmount()
        );

        return Decision.DENIED;
    }
}
class Test {
    public static void main(String[] args) {

        Approver chain =
                new TeamLead(
                        new Manager(
                                new Director(
                                        new CFO(
                                                new FallbackApprover()
                                        )
                                )
                        )
                );
        ExpenseRequest request1 =
                new ExpenseRequest(
                        4200,
                        "Team offsite"
                );
        Decision result1 =
                chain.approve(request1);

        System.out.println(
                "Team offsite: " + result1
        );
        System.out.println();
        ExpenseRequest request2 =
                new ExpenseRequest(
                        120000,
                        "New company equipment"
                );
        Decision result2 =
                chain.approve(request2);

        System.out.println(
                "Company equipment: " + result2
        );
        System.out.println();
        ExpenseRequest request3 =
                new ExpenseRequest(
                        300000,
                        "Very expensive project"
                );

        Decision result3 =
                chain.approve(request3);

        System.out.println(
                "Expensive project: " + result3
        );
    }
}