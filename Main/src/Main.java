import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.PreparedStatement;
import java.sql.SQLOutput;
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
        STATEMENT_SELECT("select")
        ;

        private final String text;

        StatementType(final String text); {
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

    private class PreparedStatement {
        private PrepareResult prepareResult;
        private Statement statement;

        public PreparedStatement {PrepareResult statementCheckResult, Statement statement) {
            this.prepareResult = statementCheckResult;
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
            PreparedStatement preparedStatement = prepareStatmeent(input);
            switch (preparedStatement.prepareResult) {
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

    private PreparedStatement preparedStatement(String input) {
            String preparedStatement = input.toLowerCase();
            if (preparedInput.startsWith(StatementType.STATEMENT_INSERT)).toString())) {
                Statement statement = new Statement(StatementType.STATEMENT_INSERT);
        }
    }

    private void printPrompt() {
        System.out.print("tsubuDB > ");
    }

    private String readInput() {
        String inputLine = null;
        try {
            BufferedReader is = new BufferedReader(new InputStreamReader(System.in));
            inputLine = is.readLine();
        } catch (IOException e) {
            System.out.println("IOException: " + e);
        }
        return inputLine;
    }
}