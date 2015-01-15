/*
 *
RCaller, A solution for calling R from Java
Copyright (C) 2010-2014  Mehmet Hakan Satman

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Lesser General Public License as published by
the Free Software Foundation, either version 3 of the License, or
any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU Lesser General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * Mehmet Hakan Satman - mhsatman@yahoo.com
 * http://www.mhsatman.com
 * Google code project: http://code.google.com/p/rcaller/
 * Please visit the blog page with rcaller label:
 * http://stdioe.blogspot.com.tr/search/label/rcaller
 */

package rcaller;

import rcaller.exception.ExecutionException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class RCode {

    protected StringBuilder code;
    protected TempFileService tempFileService = null;
    
    public void setCode(StringBuffer sb) {
        this.code = new StringBuilder();
        clear();
        this.code.append(sb);
    }

    public StringBuilder getCode() {
        return (this.code);
    }

    public RCode() {
        this.code = new StringBuilder();
        clear();
    }

    public RCode(StringBuffer sb) {
        this();
        this.code.append(sb);
    }

    public final void clear() {
        code.setLength(0);
        try {
            final InputStream is = this.getClass().getResourceAsStream("runiversal.r");
            final InputStreamReader reader = new InputStreamReader(is);
            final BufferedReader bufferedReader = new BufferedReader(reader);
            while (true) {
                final String s = bufferedReader.readLine();
                if (s == null) {
                    break;
                }
                addRCode(s);
            }
            addRCode("\n");
        } catch (IOException e) {
            throw new ExecutionException("file not found: runiversal.R in package: " + e.toString());
        }
    }
    
    public void clearOnline(){
        code.setLength(0);
    }

    public void addRCode(String code) {
        this.code.append(code).append("\n");
    }

    public void addStringArray(String name, String[] arr) {
        CodeUtils.addStringArray(code, name, arr, false);
    }

    public void addDoubleArray(String name, double[] arr) {
        CodeUtils.addDoubleArray(code, name, arr, false);
    }

    public void addFloatArray(String name, float[] arr) {
        CodeUtils.addFloatArray(code, name, arr, false);
    }

    public void addIntArray(String name, int[] arr) {
        CodeUtils.addIntArray(code, name, arr, false);
    }

    public void addShortArray(String name, short[] arr) {
        CodeUtils.addShortArray(code, name, arr, false);
    }

    public void addLogicalArray(String name, boolean[] arr) {
        CodeUtils.addLogicalArray(code, name, arr, false);
    }

    public void addJavaObject(String name, Object o) throws IllegalAccessException {
        CodeUtils.addJavaObject(code, name, o, false);
    }

    public void addDoubleMatrix(String name, double[][] matrix) {
        CodeUtils.addDoubleMatrix(code, name, matrix, false);
    }

    public void R_require(String pkg) {
        code = code.insert(0, "require(" + pkg + ")\n");
    }

    public void R_source(String sourceFile) {
        addRCode("source(\"" + sourceFile + "\")\n");
    }
    
    public void deleteTempFiles(){
        if(tempFileService != null){
            tempFileService.deleteRCallerTempFiles();   
        }
    }

    @Override
    public String toString() {
        return this.code.toString();
    }
}
