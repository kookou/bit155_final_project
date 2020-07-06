package kr.or.bit3004;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import kr.or.bit3004.controller.BoardController;

@SpringBootApplication
@ComponentScan({"kr.or.bit3004" , "controller"})
public class Bit155FinalProjectApplication {

	public static void main(String[] args) {
		new File(BoardController.uploadDirectory).mkdir();
		SpringApplication.run(Bit155FinalProjectApplication.class, args);
	}

}
