package Server.Strategies;

import Model.Model;
import Model.RecordException;

import javax.security.auth.login.FailedLoginException;
import java.io.*;

public class ServerSender implements IServerStrategy {


    public static Model model = new Model();

    @Override
    public void serverStrategy(InputStream inFromClient, OutputStream outToClient) {
        try {
            ObjectInputStream fromClient = new ObjectInputStream(inFromClient);
            ObjectOutputStream toClient = new ObjectOutputStream(outToClient);
            toClient.flush();
            StringBuilder clientCommand = (StringBuilder) fromClient.readObject();
            String command = clientCommand.toString();
            String[] array = command.split(",");
            switch (array[0]) {
                case "Login":
                    LoginServer(toClient, array);
                    break;
                case "getAllTeams":
                    getAllTeamsServer(toClient);
                    break;
                case "getAllSeasons":
                    getAllSeasonsServer(toClient);
                    break;
                case "availableSeasonsForTeam":
                    availableSeasonsForTeamServer(toClient, array[1]);
                    break;
                case "getAllLeagues":
                    getAllLeaguesServer(toClient);
                    break;
                case "getAllFields":
                    getAllFieldsServer(toClient);
                    break;
                case "createTeam":
                    createTeamServer(toClient, array);
                    break;
                case "getCoachesForTeamAtSeason":
                    getCoachesForTeamAtSeason(toClient, array);
                    break;
                case "getTeamManagersForTeamAtSeason":
                    getTeamManagersForTeamAtSeason(toClient, array);
                    break;
                case "getPlayersForTeamAtSeason":
                    getPlayersForTeamAtSeason(toClient, array);
                    break;
                case "getFieldsForTeamAtSeason":
                    getFieldsForTeamAtSeason(toClient, array);
                    break;
                case "editCoachDetails":
                    editCoachDetails(toClient, array);
                    break;
                case "editTeamManagerDetails":
                    editTeamManagerDetails(toClient, array);
                    break;
                case "editPlayerDetails":
                    editPlayerDetails(toClient, array);
                    break;
                case "editFieldDetails":
                    editFieldDetails(toClient, array);
                    break;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private void editFieldDetails(ObjectOutputStream toClient, String[] array) throws IOException {
        try {
            model.editFieldDetails(array[1], array[2], array[3], array[4],
                    array[5]);
            toClient.writeObject(new StringBuilder("Ok"));
            toClient.flush();
        } catch (RecordException e) {
            toClient.writeObject(new StringBuilder(e.getErrorMessage()));
            toClient.flush();
        }
    }

    private void editPlayerDetails(ObjectOutputStream toClient, String[] array) throws IOException {
        try {
            model.editPlayerDetails(array[1], array[2], array[3], array[4],
                    array[5], array[6]);
            toClient.writeObject(new StringBuilder("Ok"));
            toClient.flush();
        } catch (RecordException e) {
            toClient.writeObject(new StringBuilder(e.getErrorMessage()));
            toClient.flush();
        }
    }

    private void editCoachDetails(ObjectOutputStream toClient, String[] array) throws IOException {
        try {
            model.editCoachDetails(array[1], array[2], array[3], array[4],
                    array[5], array[6], array[7]);
            toClient.writeObject(new StringBuilder("Ok"));
            toClient.flush();
        } catch (RecordException e) {
            toClient.writeObject(new StringBuilder(e.getErrorMessage()));
            toClient.flush();
        }
    }
    private void editTeamManagerDetails(ObjectOutputStream toClient, String[] array) throws IOException {
        try {
            model.editTeamManagerDetails(array[1], array[2], array[3], array[4],
                    array[5]);
            toClient.writeObject(new StringBuilder("Ok"));
            toClient.flush();
        } catch (RecordException e) {
            toClient.writeObject(new StringBuilder(e.getErrorMessage()));
            toClient.flush();
        }
    }
    private void getFieldsForTeamAtSeason(ObjectOutputStream toClient, String[] array) throws IOException {
        try {
            StringBuilder answer = model.getFieldsForTeamAtSeason(array[1], array[2]);
            toClient.writeObject(answer);
            toClient.flush();
        } catch (RecordException e) {
            toClient.writeObject(new StringBuilder(e.getErrorMessage()));
            toClient.flush();
        }
    }

    private void getPlayersForTeamAtSeason(ObjectOutputStream toClient, String[] array) throws IOException {
        try {
            StringBuilder answer = model.getPlayersForTeamAtSeason(array[1], array[2]);
            toClient.writeObject(answer);
            toClient.flush();
        } catch (RecordException e) {
            toClient.writeObject(new StringBuilder(e.getErrorMessage()));
            toClient.flush();
        }
    }

    private void getTeamManagersForTeamAtSeason(ObjectOutputStream toClient, String[] array) throws IOException {
        try {
            StringBuilder answer = model.getTeamManagersForTeamAtSeason(array[1], array[2]);
            toClient.writeObject(answer);
            toClient.flush();
        } catch (RecordException e) {
            toClient.writeObject(new StringBuilder(e.getErrorMessage()));
            toClient.flush();
        }
    }

    private void getCoachesForTeamAtSeason(ObjectOutputStream toClient, String[] array) throws IOException {
        try {
            StringBuilder answer = model.getCoachesForTeamAtSeason(array[1], array[2]);
            toClient.writeObject(answer);
            toClient.flush();
        } catch (RecordException e) {
            toClient.writeObject(new StringBuilder(e.getErrorMessage()));
            toClient.flush();
        }
    }

    private void createTeamServer(ObjectOutputStream toClient, String[] array) throws IOException {
        try {
            if (model.createTeam(array[1], array[2], array[3], array[4])) {
                toClient.writeObject(new StringBuilder("Ok"));
                toClient.flush();
            }
        } catch (RecordException e) {
            toClient.writeObject(new StringBuilder(e.getErrorMessage()));
            toClient.flush();
        }
    }

    private void getAllFieldsServer(ObjectOutputStream toClient) throws IOException {
        try {
            StringBuilder answer = model.getAllFields();
            toClient.writeObject(answer);
            toClient.flush();
        } catch (RecordException e) {
            toClient.writeObject(new StringBuilder(e.getErrorMessage()));
            toClient.flush();
        }
    }

    private void getAllLeaguesServer(ObjectOutputStream toClient) throws IOException {
        try {
            StringBuilder answer = model.getAllLeagues();
            toClient.writeObject(answer);
            toClient.flush();
        } catch (RecordException e) {
            toClient.writeObject(new StringBuilder(e.getErrorMessage()));
            toClient.flush();
        }
    }

    private void availableSeasonsForTeamServer(ObjectOutputStream toClient, String teamName) throws IOException {
        StringBuilder answer = model.availableSeasonsForTeam(teamName);
        toClient.writeObject(answer);
        toClient.flush();
    }

    private void getAllSeasonsServer(ObjectOutputStream toClient) throws IOException {
        try {
            StringBuilder answer = model.getAllSeasons();
            toClient.writeObject(answer);
            toClient.flush();
        } catch (RecordException e) {
            toClient.writeObject(new StringBuilder(e.getErrorMessage()));
            toClient.flush();
        }
    }

    private void getAllTeamsServer(ObjectOutputStream toClient) throws IOException {
        try {
            StringBuilder answer = model.getAllTeams();
            toClient.writeObject(answer);
            toClient.flush();
        } catch (RecordException e) {
            toClient.writeObject(new StringBuilder(e.getMessage()));
            toClient.flush();
        }
    }

    private void LoginServer(ObjectOutputStream toClient, String[] array) throws IOException {
        try {
            if (model.login(array[1], array[2])) {
                toClient.writeObject(new StringBuilder("Ok"));
                toClient.flush();
            }
        } catch (FailedLoginException e) {
            toClient.writeObject(new StringBuilder(e.getMessage()));
            toClient.flush();
        }
    }
}