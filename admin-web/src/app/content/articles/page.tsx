'use client';

import React from 'react';
import { Card, Table, Button, Space, Tag, message } from 'antd';
import type { ColumnsType } from 'antd/es/table';
import { PlusOutlined, EditOutlined, DeleteOutlined } from '@ant-design/icons';

interface Article {
  id: number;
  title: string;
  category: string;
  tags: string[];
  author: string;
  status: 'draft' | 'published' | 'archived';
  createTime: string;
}

const mockData: Article[] = [
  {
    id: 1,
    title: '中国传统节日之春节',
    category: '传统节日',
    tags: ['春节', '传统文化'],
    author: '张三',
    status: 'published',
    createTime: '2024-01-01 12:00:00',
  },
  {
    id: 2,
    title: '中国古代四大发明',
    category: '科技发展',
    tags: ['发明', '科技'],
    author: '李四',
    status: 'draft',
    createTime: '2024-01-02 14:30:00',
  },
];

const statusMap = {
  draft: { text: '草稿', color: 'default' },
  published: { text: '已发布', color: 'success' },
  archived: { text: '已归档', color: 'warning' },
};

export default function ArticlesPage() {
  const columns: ColumnsType<Article> = [
    {
      title: 'ID',
      dataIndex: 'id',
      width: 80,
    },
    {
      title: '标题',
      dataIndex: 'title',
      ellipsis: true,
    },
    {
      title: '分类',
      dataIndex: 'category',
      width: 120,
    },
    {
      title: '标签',
      dataIndex: 'tags',
      width: 200,
      render: (tags: string[]) => (
        <Space size={[0, 4]} wrap>
          {tags.map((tag) => (
            <Tag key={tag}>{tag}</Tag>
          ))}
        </Space>
      ),
    },
    {
      title: '作者',
      dataIndex: 'author',
      width: 100,
    },
    {
      title: '状态',
      dataIndex: 'status',
      width: 100,
      render: (status: keyof typeof statusMap) => (
        <Tag color={statusMap[status].color}>{statusMap[status].text}</Tag>
      ),
    },
    {
      title: '创建时间',
      dataIndex: 'createTime',
      width: 180,
    },
    {
      title: '操作',
      key: 'action',
      width: 120,
      render: (_, record) => (
        <Space size="middle">
          <Button
            type="text"
            icon={<EditOutlined />}
            onClick={() => handleEdit(record)}
          />
          <Button
            type="text"
            danger
            icon={<DeleteOutlined />}
            onClick={() => handleDelete(record)}
          />
        </Space>
      ),
    },
  ];

  const handleAdd = () => {
    message.info('点击了添加按钮');
  };

  const handleEdit = (record: Article) => {
    message.info(`点击了编辑按钮，文章ID：${record.id}`);
  };

  const handleDelete = (record: Article) => {
    message.info(`点击了删除按钮，文章ID：${record.id}`);
  };

  return (
    <div>
      <Card
        title="文章管理"
        extra={
          <Button type="primary" icon={<PlusOutlined />} onClick={handleAdd}>
            新建文章
          </Button>
        }
      >
        <Table
          columns={columns}
          dataSource={mockData}
          rowKey="id"
          pagination={{
            total: mockData.length,
            pageSize: 10,
            showQuickJumper: true,
            showSizeChanger: true,
            showTotal: (total) => `共 ${total} 条`,
          }}
        />
      </Card>
    </div>
  );
} 