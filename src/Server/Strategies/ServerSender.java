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
            if (array[0].equals("Login")) {
                LoginServer(toClient, array);
            } else if (array[0].equals("getAllTeams")) {
                getAllTeamsServer(toClient);
            } else if (array[0].equals("getAllSeasons")) {
                getAllSeasonsServer(toClient);
            } else if (array[0].equals("availableSeasonsForTeam")) {
                availableSeasonsForTeamServer(toClient, array[1]);
            } else if (array[0].equals("getAllLeagues")) {
                getAllLeaguesServer(toClient);
            } else if (array[0].equals("getAllFields")) {
                getAllFieldsServer(toClient);
            } else if (array[0].equals("createTeam")) {
                createTeamServer(toClient,array);
            }

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void createTeamServer(ObjectOutputStream toClient, String[] array) throws IOException{
        try {
            if(model.createTeam(array[1],array[2],array[3],array[4])){
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