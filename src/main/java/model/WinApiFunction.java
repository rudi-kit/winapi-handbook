package model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(exclude = {"id", "params"})
public class WinApiFunction {

    /**
     * identifier in db
     */
    long id;

    /**
     * name of function
     */
    String name;

    /**
     * content of first description block
     */
    String description;

    /**
     * content of block which describe syntax
     */
    String syntax;

    /**
     * description of function's parameters
     */
    List<WinApiParameter> params;

    /**
     * Type of returned value
     */
    String returnType;

    /**
     * Description of returned value
     */
    String returnTypeDescription;

    /**
     * function's requirements
     */
    List<WinApiFunctionRequirement> requirements;
}
