
				
				//2) CHECK PATHS ARE VALID
				checkF1(f1_path);
				checkF1(f2_path);
				checkF1(f3_path);
				
				if (invalid==true) {
					System.out.println("This application has closed. Please next time ensure all fields submit a VALID file.");
					return;
				}	
				//ADD FILES TO ARRAYLIST ONCE VALIDATED