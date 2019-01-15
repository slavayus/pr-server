package data.db.command;

import data.db.model.Dictionary;
import data.db.repository.DictionaryRepository;

public class InserCommand extends DictionaryCommand {
    public InserCommand(DictionaryRepository repository) {
        super(repository);
    }

    @Override
    public void execute(Dictionary dictionary) {
        repository.insert(dictionary);
    }
}
