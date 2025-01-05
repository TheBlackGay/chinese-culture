import React from 'react';
import { PageContainer } from '@ant-design/pro-components';
import { Card, Typography } from 'antd';

const { Title, Paragraph } = Typography;

const HomePage: React.FC = () => {
  return (
    <PageContainer>
      <Card>
        <Typography>
          <Title level={2}>欢迎使用中国传统文化管理系统</Title>
          <Paragraph>
            本系统提供中国传统文化相关的工具和内容管理功能，包括：
          </Paragraph>
          <Paragraph>
            <ul>
              <li>八字计算：提供精准的生辰八字计算</li>
              <li>农历信息：包含节气、节日等信息</li>
              <li>更多功能持续开发中...</li>
            </ul>
          </Paragraph>
        </Typography>
      </Card>
    </PageContainer>
  );
};

export default HomePage;
