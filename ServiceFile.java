package service;

import Entity.File;
import intService.IService;

import java.sql.SQLException;
import java.util.List;

public class ServiceFile implements IService<File> {

    @Override
    public void add(File file) throws SQLException {

    }

    @Override
    public List<File> read() throws SQLException {
        return null;
    }

    @Override
    public void update(File file) throws SQLException {

    }

    @Override
    public void delete(Long id) throws SQLException {

    }
}
