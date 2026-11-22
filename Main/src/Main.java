import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class Main {

    private enum MetaCommandResult {
        META_COMMAND_SUCCESS,
        META_COMMAND_UNRECOGNIZED_COMMAND
    }

    private enum PrepareResult {
        PREPARE_SUCCESS,
        PREPARE_UNRECOGNIZED_STATEMENT
    }

    private enum StatementType {
        STATEMENT_INSERT("insert"),
        STATEMENT_SELECT("select");

        private final String text;

        StatementType(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    private class Statement {
        private final StatementType statementType;

        Statement(StatementType statementType) {
            this.statementType = statementType;
        }

        public StatementType getStatementType() {
            return statementType;
        }
    }

    private class PreparedStatementResult {
        private final PrepareResult prepareResult;
        private final Statement statement;

        public PreparedStatementResult(PrepareResult prepareResult, Statement statement) {
            this.prepareResult = prepareResult;
            this.statement = statement;
        }

        public PrepareResult getPrepareResult() {
            return prepareResult;
        }

        public Statement getStatement() {
            return statement;
        }
    }

    public static void main(String[] args) {
        new Main().run();
    }

    public void run() {
        // Executes interactive command loop; processes meta-commands and statements
        while (true) {
            printPrompt();
            String input = readInput();
            Objects.requireNonNull(input);

            if (input.isEmpty()) {
                // do nothing and loop
                continue;
            }

            if (input.charAt(0) == '.') {
                MetaCommandResult metaCommandResult = doMetaCommand(input);
                switch (metaCommandResult) {
                    case META_COMMAND_SUCCESS:
                        continue;
                    case META_COMMAND_UNRECOGNIZED_COMMAND:
                        System.out.printf("Unrecognized command: '%s'%n", input);
                        continue;
                }
            }

            PreparedStatementResult preparedStatement = prepareStatement(input);
            switch (preparedStatement.getPrepareResult()) {
                case PREPARE_SUCCESS:
                    break;
                case PREPARE_UNRECOGNIZED_STATEMENT:
                    System.out.printf("Unrecognized keyword at start of: '%s'%n", input);
                    continue;
            }

            executeStatement(preparedStatement.getStatement());
            System.out.printf("Executed.%n");
        }
    }

    private MetaCommandResult doMetaCommand(String input) {
        if (input.equals(".exit")) {
            System.out.println("Exiting - Good bye.");
            System.exit(0);
        }

        return MetaCommandResult.META_COMMAND_UNRECOGNIZED_COMMAND;
    }

    private PreparedStatementResult prepareStatement(String input) {
        String preparedInput = input.toLowerCase();

        // Identifies statement type and returns successful preparation result
        if (preparedInput.startsWith(StatementType.STATEMENT_INSERT.toString())) {
            Statement statement = new Statement(StatementType.STATEMENT_INSERT);
            return new PreparedStatementResult(PrepareResult.PREPARE_SUCCESS, statement);
        } else if (preparedInput.startsWith(StatementType.STATEMENT_SELECT.toString())) {
            Statement statement = new Statement(StatementType.STATEMENT_SELECT);
            return new PreparedStatementResult(PrepareResult.PREPARE_SUCCESS, statement);
        }

        return new PreparedStatementResult(PrepareResult.PREPARE_UNRECOGNIZED_STATEMENT, null);
    }

    private String readInput() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            return reader.readLine();
        } catch (IOException e) {
            throw new RuntimeException("Failed to read input", e);
        }
    }

    private void printPrompt() {
        System.out.print("tsubuDB > ");
    }

    private void executeStatement(Statement statement) {
        switch (statement.getStatementType()) {
            case STATEMENT_INSERT:
                System.out.println("This is where an insert would be made");
                break;
            case STATEMENT_SELECT:
                System.out.println("This is where a select would be made");
                break;
        }
    }
}