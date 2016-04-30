/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilitarios;

/**
 *
 * @author hpossani
 */
public class MemoryBlock {
    private String id;
    private final int numSeg;
    private final int sizeSeg;

    public MemoryBlock(String id, int numSeg, int sizeSeg) {
        this.id = id;
        this.numSeg = numSeg;
        this.sizeSeg = sizeSeg;
    }

    public String getId() {
        return id;
    }
    
    public void setId(String id) {
        this.id = id;
    }

    public int getNumSeg() {
        return numSeg;
    }

    public int getSizeSeg() {
        return sizeSeg;
    }  
    
}
