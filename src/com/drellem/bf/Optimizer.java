/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.drellem.bf;

import com.drellem.bf.Token.TokenType;

/**
 *
 * @author Daniel Miller <a href="mailto:gate46dmiller@gmail.com">gate46dmiller@gmail.com</a>
 */
public class Optimizer {
    private TokenStream tokens;
    private ASTree tree = new ASTree();
    private Node node = new Node();
    private OpPass[] passes = {};
    
    public Optimizer(TokenStream tokens){
        this.tokens = tokens;
    }
    
    public ASTree run(){
        tree = parse(false, tokens);
        return tree;
    }
    
    
    public ASTree parse(boolean inLoop, TokenStream tokens){
        ASTree tree = new ASTree();
        while(tokens.hasNext()){
            Token t = tokens.getNext();
            switch(t.getType()){
                case PLUS:
                    if(t.getValue().equals("")){
                        tree.append(node.plusNode(0,1));
                        break;
                    }
                    tree.append(node.plusNode(0, Integer.parseInt(t.getValue())));
                    break;
                    
                case MINUS:
                    if(t.getValue().equals("")){
                        tree.append(node.minusNode(0,1));
                        break;
                    }
                    tree.append(node.minusNode(0, Integer.parseInt(t.getValue())));
                    break;
                    
                case INC:
                    if(t.getValue().equals("")){
                        tree.append(node.incNode(1));
                        break;
                    }
                    tree.append(node.incNode(Integer.parseInt(t.getValue())));
                    break;
                    
                case DEC:
                    if(t.getValue().equals("")){
                        tree.append(node.decNode(1));
                        break;
                    }
                    tree.append(node.decNode(Integer.parseInt(t.getValue())));
                    break;
                    
                case PUT:
                    tree.append(node.putNode(0));
                    break;
                    
                case GET:
                    tree.append(node.getNode(0));
                    break;
                    
                case LOOP:
                    Node n = node.loopNode();
                    n.addTree(parse(true, tokens));
                    break;
                    
                case END:
                    if(!inLoop){
                        System.err.println("Fatal error: unexpected ']' token");
                        System.exit(1);
                    }
                    else return tree;
                    break;
                    
                default:
                    System.err.println("Unrecognized token type " + t.getClass() + " with value " + t.getValue());
                    System.exit(0);
                    break;
                    
                    
            }
        }
        return tree;
    }
}
