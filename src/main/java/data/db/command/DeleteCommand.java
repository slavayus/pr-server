package data.db.command;

import data.db.model.Dictionary;
import data.db.repository.DictionaryRepository;

public class DeleteCommand extends DictionaryCommand {
    public DeleteCommand(DictionaryRepository repository) {
        super(repository);
    }

    @Override
    public void execute(Dictionary dictionary) {
        repository.delete(dictionary);
    }
}
