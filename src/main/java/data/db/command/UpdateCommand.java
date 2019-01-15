package data.db.command;

import data.db.model.Dictionary;
import data.db.repository.DictionaryRepository;

public class UpdateCommand extends DictionaryCommand {
    public UpdateCommand(DictionaryRepository repository) {
        super(repository);
    }

    @Override
    public void execute(Dictionary dictionary) {
        repository.update(dictionary);
    }
}
