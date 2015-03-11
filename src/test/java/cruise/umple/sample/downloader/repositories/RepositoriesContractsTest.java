package cruise.umple.sample.downloader.repositories;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;

import java.net.URL;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

import org.testng.annotations.Guice;
import org.testng.annotations.Test;

import com.google.inject.Inject;

import cruise.umple.sample.downloader.DownloaderModule;
import cruise.umple.sample.downloader.FileType;
import cruise.umple.sample.downloader.Repository;
import cruise.umple.sample.downloader.entities.ImportEntity;
import cruise.umple.sample.downloader.util.MockDocumentFactoryModule;
import cruise.umple.sample.downloader.util.Pair;

/**
 * This Test will test all loaded repositories to make sure that when inheriting from the {@link Repository} interface, 
 * they match the contracts outlined. This test may be slow due to it fetching the result of 
 * {@link Repository#getImports()} for every loaded {@link Repository}. 
 * 
 * @author Kevin Brightwell <kevin.brightwell2@gmail.com>
 * 
 * @since 24 Feb 2015
 *
 */
@Guice(modules={MockDocumentFactoryModule.class})
public class RepositoriesContractsTest {
    
    @Inject
    private Set<Repository> allRepositories;
    
    @Inject
    private Logger logger;
    
    /**
     * Check all names to adhere to {@link Repository#getName()} specifications 
     * @since Feb 24, 2015
     */
    @Test
    public void checkNames() {
      for (Repository r : allRepositories) {
        assertNotNull(r.getName(), "Repository had a null name.");
        assertFalse(r.getName().isEmpty(), "Repository had an empty name.");
      }
    }
    
    /**
     * Check all descriptions to adhere to {@link Repository#getDescription()} specifications 
     * @since Feb 24, 2015
     */
    @Test
    public void checkDescriptions() {
      for (Repository r : allRepositories) {
        assertNotNull(r.getDescription(), "Repository had a null name.");
        assertFalse(r.getDescription().isEmpty(), "Repository had an empty name.");
      }
    }
    
    /**
     * Check all {@link FileType} to adhere to {@link Repository#getFileType()} specifications 
     * @since Feb 24, 2015
     */
    @Test
    public void checkFileType() {
      for (Repository r : allRepositories) {
        assertNotNull(r.getFileType(), "Repository, " + r.getName() + ", has a null FileType.");
      }
    }
    
    /**
     * Check all returned {@link Repository}, {@link URL} {@link Pair} instances to adhere to 
     * {@link Repository#getImports()} specifications.
     * 
     * @since Feb 24, 2015
     */
    @Test
    public void checkURLs() {
      logger.finer("Running Repository tests, these may take time depending on internet connection and repositories.");
      for (Repository r : allRepositories) {
        logger.finest("Repository: " + r.getName());
        
        List<ImportEntity> imports = r.getImports();
        assertNotNull(imports, "urls response was null");
        
        for (final ImportEntity ie : imports) {
          assertNotNull(ie, "Triple was null");
          assertEquals(ie.getRepository(), r, "Import from Repository, " + r.getName() + ", do not have proper Repository set");
          assertNotNull(ie.getPath(), "Import path from Repository, " + r.getName() + ", was null.");
          assertNotNull(ie.get(), "Import InputStream from Repository, " + r.getName() + ", was null.");
        }
      }
    }
  
}
