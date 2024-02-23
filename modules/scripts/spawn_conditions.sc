__config() -> {
    'scope' -> 'global',
    'resources' -> [
        {
            'source' -> '/configs/entity_blacklist.json',
            'target' -> 'entity_blacklist.json',
            'shared' -> true,
        },
    ],
};

config_data = read_file('entity_blacklist','shared_json');
print(config_data)
