import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.time.temporal.JulianFields;

public class Coordinate extends JDialog implements ActionListener {
    JButton jButton;
    int dataSet[][];
    int CLUSTER,DATA_AMOUNT;
    int C[][];
    float U[][];
    float preJ,J;

    Coordinate(int CLUSTER,int DATA_AMOUNT)
    {
        this.CLUSTER=CLUSTER;
        this.DATA_AMOUNT=DATA_AMOUNT;

        C=new int[CLUSTER][2];
        U=new float[CLUSTER][DATA_AMOUNT];
        preJ=-1;
        J=(float)0;

        dataSet=new int[DATA_AMOUNT][2];
        this.getDataSet();//获取数据集

        //初始化Uij和Ci
        this.initC();

        this.setLayout(null);

        jButton=new JButton("下一次迭代");
        jButton.setBounds(640,300,100,30);
        jButton.addActionListener(this);

        this.add(jButton);
        this.setBounds(520,220,800,700);
        this.setVisible(true);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);

        drawCoordinate(g);//画横纵坐标


        for(int i=0;i<DATA_AMOUNT;i++)
        {
            int centerI=makesureCenter(i);//确定该点离哪个中心点更近，好涂颜色
            g=chooseColor(g,centerI);
            g.fillOval( 100+dataSet[i][0],600-dataSet[i][1],5,5);
        }

        for(int i=0;i<CLUSTER;i++)
        {
            g.setColor(Color.RED);
            g.fillOval( 100+C[i][0],600-C[i][1],8,8);
        }

    }

    //确定点的颜色
    public Graphics chooseColor(Graphics g,int i)
    {
        switch (i)
        {
            case 0:g.setColor(Color.orange);break;
            case 1:g.setColor(Color.black);break;
            case 2:g.setColor(Color.magenta);break;
            case 3:g.setColor(Color.pink);break;
            case 4:g.setColor(Color.GRAY);break;
            case 5:g.setColor(Color.BLUE);break;
            case 6:g.setColor(Color.darkGray);break;
            case 7:g.setColor(Color.YELLOW);break;
            case 8:g.setColor(Color.LIGHT_GRAY);break;
            case 9:g.setColor(Color.GREEN);break;
            default:g.setColor(Color.BLUE);
        }
        return g;
    }

    //计算距离数据点最近的中心点，返回中心点的数组下标i
    public int makesureCenter(int dataSetI)
    {
        int centerI=-1;
        int distance;
        int minDistance=500*500;
        for(int i=0;i<CLUSTER;i++)
        {
            distance= (int)(Math.pow(dataSet[dataSetI][0]-C[i][0],2)+ Math.pow(dataSet[dataSetI][1]-C[i][1],2));
            if(distance<minDistance)
            {
                minDistance=distance;
                centerI=i;
            }
        }
        return centerI;
    }


    //获得数据集
    public int[][] getDataSet() {
        FileInputStream fileInputStream= null;
        try {
            fileInputStream = new FileInputStream(System.getProperty("user.dir")+"/DataSource/urbanGB.labels.txt");
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(fileInputStream));
            String strTmp;
            //给数据集赋值
            int i=0,j=0;
            while (i<DATA_AMOUNT&&(strTmp = bufferedReader.readLine())!=null)
            {
                if(j==2)
                {
                    j=0;
                    i++;
                }
                if(i<DATA_AMOUNT)
                {
                    dataSet[i][j]=Integer.parseInt(strTmp);
                    j++;
                }

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return dataSet;
    }

    //画坐标轴
    public void drawCoordinate(Graphics g)
    {
        //横坐标
        g.drawLine(100,600,600,600);

        g.drawLine(200,600,200,605);
        g.drawLine(300,600,300,605);
        g.drawLine(400,600,400,605);
        g.drawLine(500,600,500,605);
        g.drawString("0",100,630);
        g.drawString("100",200,630);
        g.drawString("200",300,630);
        g.drawString("300",400,630);
        g.drawString("400",500,630);
        g.drawString("500",600,630);


        //纵坐标
        g.drawLine(100,100,100,600);

        g.drawLine(95,200,100,200);
        g.drawLine(95,300,100,300);
        g.drawLine(95,400,100,400);
        g.drawLine(95,500,100,500);
        g.drawString("0",70,600);
        g.drawString("100",70,500);
        g.drawString("200",70,400);
        g.drawString("300",70,300);
        g.drawString("400",70,200);
        g.drawString("500",70,100);


    }

    //获取Ci
    public void setC()
    {
        float x1=0,x2=0,x3=0;
        for(int i=0;i<CLUSTER;i++)
        {
            for(int j=0;j<DATA_AMOUNT;j++)
            {
                x1+=Math.pow(U[i][j],2)*dataSet[j][0];
                x2+=Math.pow(U[i][j],2)*dataSet[j][1];
                x3+=Math.pow(U[i][j],2);
            }
            C[i][0]=(int)(x1/x3);
            C[i][1]=(int)(x2/x3);
            x1=0;
            x2=0;
            x3=0;
        }
    }

    //初始化Ci
    public void initC()
    {
        for(int i=0;i<CLUSTER;i++)
            for(int j=0;j<2;j++)
            {
                C[i][j]=(int)(Math.random()*500)+1;
            }
    }

    //获取Uij
    public void setU()
    {
        float x1= (float) 0;
        for(int i=0;i<CLUSTER;i++)
            for(int j=0;j<DATA_AMOUNT;j++)
            {
                for(int k=0;k<CLUSTER;k++)
                {
                    x1+=(float) ((Math.pow(dataSet[j][0]-C[i][0],2)+Math.pow(dataSet[j][1]-C[i][1],2)) / (Math.pow(dataSet[j][0]-C[k][0],2)+Math.pow(dataSet[j][1]-C[k][1],2)));
                }
                x1=(float)(1/x1);
                U[i][j]=x1;
                x1=0;
            }
    }

    //获取目标函数J
    public void setJ()
    {
        J=0;
        for(int i=0;i<CLUSTER;i++)
            for(int j=0;j<DATA_AMOUNT;j++)
            {
                J+= (float)(Math.pow(U[i][j],2)*(Math.pow(dataSet[j][0]-U[i][0],2)+Math.pow(dataSet[j][1]-U[i][1],2)));
            }
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==jButton)
        {
            this.setU();
            this.setC();
            this.repaint();

            this.setJ();
            if(Math.abs(preJ-J)<0.01)
                jButton.setText("已完成聚类");
            preJ=J;

        }
    }
}
