package data.db.command;

import data.db.model.Dictionary;
import data.db.repository.DictionaryRepository;

public class SelectCommand extends DictionaryCommand {
    public SelectCommand(DictionaryRepository repository) {
        super(repository);
    }

    @Override
    public void execute(Dictionary dictionary) {
        repository.select(dictionary);
    }
}
